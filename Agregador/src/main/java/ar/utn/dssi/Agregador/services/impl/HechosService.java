package ar.utn.dssi.Agregador.services.impl;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.HechoInputDTO;
import ar.utn.dssi.Agregador.models.DTOs.outputDTO.HechoOutputDTO;
import ar.utn.dssi.Agregador.models.entities.content.Categoria;
import ar.utn.dssi.Agregador.models.entities.content.Hecho;
import ar.utn.dssi.Agregador.models.entities.content.Ubicacion;
import ar.utn.dssi.Agregador.models.repositories.IHechosRepository;
import ar.utn.dssi.Agregador.services.IColeccionService;
import ar.utn.dssi.Agregador.services.IFuentesService;
import ar.utn.dssi.Agregador.services.IHechosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.testcontainers.shaded.org.apache.commons.lang3.ObjectUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.stream.Stream;
import java.util.List;


@Service
public class HechosService implements IHechosService {
    @Autowired
    private IHechosRepository hechosRepository;

    @Autowired
    private IColeccionService coleccionService;

    @Autowired
    private IFuentesService fuentesService;

    @Override
    public Mono<Void> actualizarHechos() {
        try {
            return Flux
                .fromIterable(this.importarNuevosHechos())
                .flatMap(hecho -> {
                    coleccionService.refrescarColecciones(hecho);
                    return Mono.empty();
                })
                .then();
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar los hechos: " + e.getMessage(), e);
        }
    }

    private List<Hecho> importarNuevosHechos() {
        try {
            List<Hecho> hechosNuevos = fuentesService.obtenerNuevosHechos();

            return hechosNuevos;
        } catch (Exception e) {
            throw new RuntimeException("Error al importar los hechos: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminarHecho(Long IdHecho) {
        try {
            //TODO revisar gestion de eliminacion en fuente si es estatica o dinamica => ver que fuente es y mandarle que lo elimine
            Hecho hecho = this.hechosRepository.findById(IdHecho);
            hecho.setVisible(false);
            hechosRepository.update(hecho);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el hecho: " + e.getMessage(), e);
        }
    }

    @Override
    public HechoOutputDTO obtenerHechoPorId(Long idHecho) {
        try {
            Hecho hecho = hechosRepository.findById(idHecho);

            return hechoOutputDTO(hecho);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el hecho por id: " + e.getMessage(), e);
        }
    }

    @Override
    public List<HechoOutputDTO> obtenerHechos() {
        try {
            var hechos = this.hechosRepository.findall().stream()
                .map(this::hechoOutputDTO)
                .toList();;
            var hechosProxy = this.fuentesService.obtenerHechosProxy()
                .stream()
                .map(this::hechoOutputDTOProxy)
                .toList();

            if (hechos.isEmpty() && (hechosProxy == null || hechosProxy.isEmpty())) {
                throw new RuntimeException("No hay hechos disponibles");
            }

            return Stream.concat(
                hechos.stream(),
                hechosProxy.stream()
            ).toList();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener los hechos: " + e.getMessage(), e);
        }
    }

    @Override
    public Hecho crearHecho(HechoInputDTO hechoInputDTO, Long IdFuente) {
        try {
            var hecho = new Hecho();
            var ubicacion = new Ubicacion(hechoInputDTO.getUbicacion().getLatitud(), hechoInputDTO.getUbicacion().getLongitud());
            var categoria = new Categoria();

            hecho.setTitulo(hechoInputDTO.getTitulo());
            hecho.setDescripcion(hechoInputDTO.getDescripcion());
            hecho.setFechaAcontecimiento(hechoInputDTO.getFechaAcontecimiento());
            hecho.setFechaCarga(LocalDateTime.now());
            hecho.setUbicacion(ubicacion);
            hecho.setCategoria(categoria);
            hecho.setOrigen(hechoInputDTO.getOrigen());
            hecho.setVisible(true);
            hecho.setContenidoMultimedia(hechoInputDTO.getContenidoMultimedia());
            hecho.setIdHecho(hechosRepository.obtenerUltimoId());
            hecho.setIdOrigen(hechoInputDTO.getIdEnFuente());
            hecho.setIdFuente(IdFuente);

            return hecho;
        } catch (Exception e) {
            throw new RuntimeException("Error al crear el hecho: " + e.getMessage(), e);
        }
    }

    public void guardarHecho(Hecho hecho){
        try {
            Long idEnFuente = hecho.getIdOrigen();
            Long idFuente = hecho.getIdFuente();

            Hecho hechoObtenido = hechosRepository.findByIdOrigenAndIdFuente(idEnFuente, idFuente);

            if(hechoObtenido == null) {
                hechosRepository.save(hecho);
            } else {
                hechosRepository.update(hecho);
            }
        } catch(Exception e) {
            throw new RuntimeException("Error al guardar el hecho: " + e.getMessage(), e);
        }
    }

    @Override
    public HechoOutputDTO hechoOutputDTO(Hecho hecho) { //Lo vamos a usar cuando queremos mostrar los hechos de la coleccion
        try{
            var dtoHecho = new HechoOutputDTO();

            dtoHecho.setTitulo(hecho.getTitulo());
            dtoHecho.setDescripcion(hecho.getDescripcion());
            dtoHecho.setCategoria(hecho.getCategoria());
            dtoHecho.setUbicacion(hecho.getUbicacion());
            dtoHecho.setFechaAcontecimiento(hecho.getFechaAcontecimiento());
            dtoHecho.setFechaCarga(hecho.getFechaCarga());
            dtoHecho.setContenidoMultimedia(hecho.getContenidoMultimedia());

            return dtoHecho;
        } catch(Exception e) {
            throw new RuntimeException("Error al obtener el dto de hecho: " + e.getMessage(), e);
        }
    }

    public HechoOutputDTO hechoOutputDTOProxy(HechoInputDTO hecho) { //Lo vamos a usar cuando queremos mostrar los hechos de la fuenteProxy
        try {
            var dtoHecho = new HechoOutputDTO();

            dtoHecho.setTitulo(hecho.getTitulo());
            dtoHecho.setDescripcion(hecho.getDescripcion());
            dtoHecho.setCategoria(hecho.getCategoria());
            dtoHecho.setUbicacion(hecho.getUbicacion());
            dtoHecho.setFechaAcontecimiento(hecho.getFechaAcontecimiento());
            dtoHecho.setFechaCarga(hecho.getFechaCarga());
            dtoHecho.setContenidoMultimedia(hecho.getContenidoMultimedia());

            return dtoHecho;
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el dto de hecho desde fuente proxy: " + e.getMessage(), e);
        }

    }
}