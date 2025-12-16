package ar.utn.dssi.Agregador.services.impl;

import ar.utn.dssi.Agregador.dto.input.SolicitudDeEliminacionInputDTO;
import ar.utn.dssi.Agregador.dto.input.SolicitudProcesadaInputDTO;
import ar.utn.dssi.Agregador.dto.output.SolicitudDeEliminacionOutputDTO;
import ar.utn.dssi.Agregador.error.HechoNoEcontrado;
import ar.utn.dssi.Agregador.error.SolicitudNoEncontrada;
import ar.utn.dssi.Agregador.error.SolicitudYaProcesada;
import ar.utn.dssi.Agregador.mappers.MapperDeSolicitudesDeEliminacion;
import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.solicitud.EstadoDeSolicitud;
import ar.utn.dssi.Agregador.models.entities.solicitud.SolicitudDeEliminacion;
import ar.utn.dssi.Agregador.models.entities.solicitud.SolicitudDeEliminacionFactory;
import ar.utn.dssi.Agregador.models.entities.spam.DetectorDeSpam;
import ar.utn.dssi.Agregador.models.repositories.IHechosRepository;
import ar.utn.dssi.Agregador.models.repositories.ISolicitudDeEliminacionRepository;
import ar.utn.dssi.Agregador.services.IHechosService;
import ar.utn.dssi.Agregador.services.ISolicitudDeEliminacionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class SolicitudDeEliminacionService implements ISolicitudDeEliminacionService {
  private final ISolicitudDeEliminacionRepository solicitudDeEliminacionRepository;
  private final IHechosService hechosService;
  private final IHechosRepository hechosRepository;
  private final SolicitudDeEliminacionFactory solicitudDeEliminacionFactory;
  private final SolicitudDeEdicionService solicitudDeEdicionService;

  @Override
  public SolicitudDeEliminacionOutputDTO crearSolicitudDeEliminacion(SolicitudDeEliminacionInputDTO solicitudDeEliminacion) {
    Hecho hecho = hechosRepository.findById(solicitudDeEliminacion.getIdHecho())
        .orElseThrow(() -> new HechoNoEcontrado("No se encontró el hecho con ID: " + solicitudDeEliminacion.getIdHecho()));
    SolicitudDeEliminacion solicitud = solicitudDeEliminacionFactory.crear(hecho, solicitudDeEliminacion.getDescripcion());

    if (DetectorDeSpam.esSpam(solicitud.getDescripcion())) {
      solicitud.setEsSpam(true);
      solicitud.rechazar();
      log.info("Solicitud marcada como SPAM y rechazada automáticamente");  //TODO: HABRIA QUE DEVOLVER UNA EXCEPCION
    } else {
      solicitud.setEsSpam(false);
      solicitud.setEstadoDeSolicitud(EstadoDeSolicitud.PENDIENTE);
      log.info("Solicitud creada y en estado PENDIENTE");
    }
    solicitud.setAutor(obtenerUsuarioActual());
    this.solicitudDeEliminacionRepository.save(solicitud);

    return MapperDeSolicitudesDeEliminacion.outpuDTOFromSolicitudDeEliminacion(solicitud);
  }

  @Override
  @Transactional
  public void procesarSolicitud(Long idSolicitud, SolicitudProcesadaInputDTO solicitudProcesada) {
    SolicitudDeEliminacion solicitud = solicitudDeEliminacionRepository.findById(idSolicitud)
        .orElseThrow(() -> new NoSuchElementException("No se encontró la solicitud con ID: " + idSolicitud));

    if (!solicitud.getEstadoDeSolicitud().equals(EstadoDeSolicitud.PENDIENTE)) {
      throw new SolicitudYaProcesada("Solicitud " + solicitud.getIdSolicitud().toString() + " ya procesada como " + solicitud.getEstadoDeSolicitud());
    }

    EstadoDeSolicitud estado = MapperDeSolicitudesDeEliminacion.estadoDeSolicitudFromString(solicitudProcesada.getEstado());

    if (estado == EstadoDeSolicitud.ACEPTADA) {
      solicitud.aceptar();
      hechosService.eliminarHecho(solicitud.getHecho().getId());
      solicitudDeEdicionService.eliminarSolicitudesPorHechoId(solicitud.getHecho().getId());
    } else {
      solicitud.rechazar();
    }
    solicitud.setAutor(obtenerUsuarioActual());
    this.solicitudDeEliminacionRepository.save(solicitud);

    log.info("Solicitud {}{}{}", solicitud.getIdSolicitud().toString(), " procesada como ", solicitud.getEstadoDeSolicitud().toString());
  }

  @Override
  public List<SolicitudDeEliminacionOutputDTO> obtenerSolicitudes(String tipoEstado, Boolean spam) {
    EstadoDeSolicitud estadoDeSolicitud = null;

    if (tipoEstado != null)
      estadoDeSolicitud = MapperDeSolicitudesDeEliminacion.estadoDeSolicitudFromString(tipoEstado);

    List<SolicitudDeEliminacion> solicitudes = this.solicitudDeEliminacionRepository.findByFiltro(estadoDeSolicitud, spam);

    return solicitudes.stream()
        .map(MapperDeSolicitudesDeEliminacion::outpuDTOFromSolicitudDeEliminacion)
        .toList();
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

  public SolicitudDeEliminacionOutputDTO obtenerSolicitudPorId(Long id) {
    SolicitudDeEliminacion solicitud = solicitudDeEliminacionRepository.findById(id)
            .orElseThrow(() -> new SolicitudNoEncontrada("La solicitud con id " + id + " no existe."));

    return MapperDeSolicitudesDeEliminacion.outpuDTOFromSolicitudDeEliminacion(solicitud);
  }
}