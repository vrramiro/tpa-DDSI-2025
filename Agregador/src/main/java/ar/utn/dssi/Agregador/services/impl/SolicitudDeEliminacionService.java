package ar.utn.dssi.Agregador.services.impl;

import ar.utn.dssi.Agregador.models.entities.content.Hecho;
import ar.utn.dssi.Agregador.models.repositories.IHechosRepository;
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

  @Autowired
  private IHechosRepository hechosRepository;

  @Override
  public void crearSolicitudDeEliminacion(SolicitudDeEliminacionInputDTO solicitudDeEliminacion){
    var solicitud = new SolicitudDeEliminacion();

    solicitud.setIDHecho(solicitudDeEliminacion.getIDHecho());
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
  public void aceptarSolicitud(Long idSolicitud){
    SolicitudDeEliminacion solicitud= solicitudDeEliminacionRepository.findById(idSolicitud);
    solicitud.setEstadoDeSolicitud(EstadoDeSolicitud.ACEPTADA);
    hechosRepository.findById(solicitud.getIDHecho()).setVisible(false);
    solicitud.setFechaDeEvaluacion(LocalDateTime.now());
    hechosService.eliminarHecho(solicitud.getIDHecho());

    solicitudDeEliminacionRepository.update(solicitud);
  }

  @Override
  public void rechazarSolicitud(Long idSolicitud){
       SolicitudDeEliminacion solicitud = solicitudDeEliminacionRepository.findById(idSolicitud);
       solicitud.setEstadoDeSolicitud(EstadoDeSolicitud.RECHAZADA);
       solicitud.setFechaDeEvaluacion(LocalDateTime.now());

       solicitudDeEliminacionRepository.update(solicitud);
     }

     public void setDescripcion(String descripcion, SolicitudDeEliminacion solicitudDeEliminacion) {
       if (descripcion == null || descripcion.length() < SolicitudDeEliminacion.getCaracteresMinimos()) {
         throw new IllegalArgumentException("La descripción debe tener minimo " + SolicitudDeEliminacion.getCaracteresMinimos() );
       }
       solicitudDeEliminacion.setDescripcion(descripcion);
     }
}