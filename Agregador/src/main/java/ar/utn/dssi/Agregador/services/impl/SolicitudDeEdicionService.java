package ar.utn.dssi.Agregador.services.impl;

import ar.utn.dssi.Agregador.dto.input.SolicitudEdicionInputDTO;
import ar.utn.dssi.Agregador.dto.output.SolicitudEdicionOutputDTO;
import ar.utn.dssi.Agregador.error.HechoNoEcontrado;
import ar.utn.dssi.Agregador.mappers.MapperDeSolicitudesEdicion;
import ar.utn.dssi.Agregador.models.entities.Categoria;
import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.fuente.TipoFuente;
import ar.utn.dssi.Agregador.models.entities.normalizadorAdapter.INormalizadorAdapter;
import ar.utn.dssi.Agregador.models.entities.solicitud.EstadoDeSolicitud;
import ar.utn.dssi.Agregador.models.entities.solicitud.SolicitudDeEdicion;
import ar.utn.dssi.Agregador.models.repositories.IHechosRepository;
import ar.utn.dssi.Agregador.models.repositories.ISolicitudDeEdicionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SolicitudDeEdicionService {

    private final ISolicitudDeEdicionRepository solicitudRepository;
    private final IHechosRepository hechosRepository;
    private final INormalizadorAdapter normalizadorAdapter;

    @Transactional
    public SolicitudDeEdicion crearSolicitud(Long idHecho, SolicitudEdicionInputDTO dto) {
        Hecho hecho = hechosRepository.findById(idHecho)
                .orElseThrow(() -> new HechoNoEcontrado("Hecho no encontrado: " + idHecho));

        if (hecho.getFuente().getTipoFuente().getTipoFuente() != TipoFuente.DINAMICA) {
            throw new IllegalArgumentException("Solo se pueden editar hechos de fuentes dinámicas.");
        }

        SolicitudDeEdicion solicitud = new SolicitudDeEdicion();
        solicitud.setHechoOriginal(hecho);

        solicitud.setNuevoTitulo(dto.getTitulo());
        solicitud.setNuevaDescripcion(dto.getDescripcion());
        solicitud.setNuevoIdCategoria(dto.getIdCategoria());

        solicitud.setEstado(EstadoDeSolicitud.PENDIENTE);
        solicitud.setFechaCreacion(LocalDateTime.now());

        solicitud.setAutor(obtenerUsuarioActual());

        return solicitudRepository.save(solicitud);
    }

    @Transactional
    public void procesarSolicitud(Long idSolicitud, String accion, SolicitudEdicionInputDTO modificaciones) {
        SolicitudDeEdicion solicitud = solicitudRepository.findById(idSolicitud)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        if (solicitud.getEstado() != EstadoDeSolicitud.PENDIENTE) {
            throw new RuntimeException("La solicitud ya fue procesada.");
        }

        if ("ACEPTAR".equalsIgnoreCase(accion)) {
            if (modificaciones != null) {
                if (modificaciones.getTitulo() != null && !modificaciones.getTitulo().isBlank()) {
                    solicitud.setNuevoTitulo(modificaciones.getTitulo());
                }
                if (modificaciones.getDescripcion() != null && !modificaciones.getDescripcion().isBlank()) {
                    solicitud.setNuevaDescripcion(modificaciones.getDescripcion());
                }
                if (modificaciones.getIdCategoria() != null) {
                    solicitud.setNuevoIdCategoria(modificaciones.getIdCategoria());
                }
                solicitudRepository.save(solicitud);
            }

            aplicarCambios(solicitud);
            solicitud.setEstado(EstadoDeSolicitud.ACEPTADA);
        } else {
            solicitud.setEstado(EstadoDeSolicitud.RECHAZADA);
        }

        solicitud.setFechaEvaluacion(LocalDateTime.now());
        solicitud.setGestionadoPor(obtenerUsuarioActual());
        solicitudRepository.save(solicitud);
    }

    private void aplicarCambios(SolicitudDeEdicion solicitud) {
        Hecho hecho = solicitud.getHechoOriginal();

        if (solicitud.getNuevoTitulo() != null && !solicitud.getNuevoTitulo().isBlank()) {
            hecho.setTitulo(solicitud.getNuevoTitulo());
        }

        if (solicitud.getNuevaDescripcion() != null && !solicitud.getNuevaDescripcion().isBlank()) {
            hecho.setDescripcion(solicitud.getNuevaDescripcion());
        }

        Long nuevoIdCat = solicitud.getNuevoIdCategoria();
        if (nuevoIdCat != null && !nuevoIdCat.equals(hecho.getCategoria().getId())) {

            Categoria categoriaNormalizada = normalizadorAdapter.obtenerCategoriaPorId(nuevoIdCat)
                    .orElseThrow(() -> new RuntimeException("No se pudo obtener la categoría del normalizador ID: " + nuevoIdCat));

            hecho.setCategoria(categoriaNormalizada);
        }

        hechosRepository.save(hecho);
    }

    public List<SolicitudEdicionOutputDTO> obtenerPendientesDTO() {
        return solicitudRepository.findByEstado(EstadoDeSolicitud.PENDIENTE)
                .stream()
                .map(MapperDeSolicitudesEdicion::toDTO)
                .collect(Collectors.toList());
    }

    private String obtenerUsuarioActual() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null
                && authentication.isAuthenticated()
                && !authentication.getPrincipal().equals("anonymousUser")) {
            return authentication.getName();
        }
        return null;
    }
}