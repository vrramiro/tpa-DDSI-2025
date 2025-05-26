package ar.utn.dssi.Agregador.services.impl;

import ar.utn.dssi.Agregador.spam.DetectorDeSpam;
import ar.utn.dssi.Agregador.models.DTOs.inputDTO.SolicitudDeEliminacionInputDTO;
import ar.utn.dssi.Agregador.models.entities.solicitud.EstadoDeSolicitud;
import ar.utn.dssi.Agregador.models.entities.solicitud.SolicitudDeEliminacion;
import ar.utn.dssi.Agregador.models.repositories.impl.SolicitudDeEliminacionRepository;
import ar.utn.dssi.Agregador.services.IHechosService;
import ar.utn.dssi.Agregador.services.ISolicitudDeEliminacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class SolicitudDeEliminacionService implements ISolicitudDeEliminacionService {

  @Autowired
  private SolicitudDeEliminacionRepository solicitudDeEliminacionRepository;

  @Autowired
  private IHechosService hechosService;

  @Override
  public void crearSolicitudDeEliminacion(SolicitudDeEliminacionInputDTO solicitudDeEliminacion){
    var solicitud = new SolicitudDeEliminacion();

    solicitud.setHecho(hechosService.obtenerHechoPorId(solicitudDeEliminacion.getIdHecho()));
    solicitud.setFechaDeCreacion(LocalDateTime.now());
    setDescripcion(solicitudDeEliminacion.getDescripcion(), solicitud);

    if (DetectorDeSpam.esSpam(solicitud.getDescripcion())) {
      solicitud.setEsSpam(true);
      solicitud.setFechaDeEvaluacion(LocalDateTime.now());
      solicitud.setEstadoDeSolicitud(EstadoDeSolicitud.RECHAZADA);
    } else {
      solicitud.setEsSpam(false);
      solicitud.setEstadoDeSolicitud(EstadoDeSolicitud.PENDIENTE);
    }

    solicitudDeEliminacionRepository.save(solicitud);
  }

  @Override
  public void aceptarSolicitud(SolicitudDeEliminacion solicitudDeEliminacion){
    solicitudDeEliminacion.setEstadoDeSolicitud(EstadoDeSolicitud.ACEPTADA);
    solicitudDeEliminacion.getHecho().setVisible(false);
    solicitudDeEliminacion.setFechaDeEvaluacion(LocalDateTime.now());
    hechosService.eliminarHecho(solicitudDeEliminacion.getHecho());

    solicitudDeEliminacionRepository.update(solicitudDeEliminacion);
  }

  @Override
  public void rechazarSolicitud(SolicitudDeEliminacion solicitudDeEliminacion) {
    solicitudDeEliminacion.setEstadoDeSolicitud(EstadoDeSolicitud.RECHAZADA);
    solicitudDeEliminacion.setFechaDeEvaluacion(LocalDateTime.now());

    solicitudDeEliminacionRepository.update(solicitudDeEliminacion);
  }

  public void setDescripcion(String descripcion, SolicitudDeEliminacion solicitudDeEliminacion) {
    if (descripcion == null || descripcion.length() < SolicitudDeEliminacion.getCaracteresMinimos()) {
      throw new IllegalArgumentException("La descripción debe tener minimo " + SolicitudDeEliminacion.getCaracteresMinimos() );
    }
    solicitudDeEliminacion.setDescripcion(descripcion);
  }
}