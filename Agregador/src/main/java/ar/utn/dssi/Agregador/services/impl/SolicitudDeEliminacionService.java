package ar.utn.dssi.Agregador.services.impl;

import ar.utn.dssi.Agregador.models.DTOs.outputDTO.SolicitudDeEliminacionOutputDTO;
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

  //CREAR SOLICITUDES DE ELIMINACION
  @Override
  public SolicitudDeEliminacionOutputDTO crearSolicitudDeEliminacion(SolicitudDeEliminacionInputDTO solicitudDeEliminacion){
    var solicitud = new SolicitudDeEliminacion();

    solicitud.setIdHecho(solicitudDeEliminacion.getIdHecho());
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

    return this.SolicitudEliminacionOutputDTO(solicitud);
  }

  //ACEPTAR O RECHAZAR SOLICITUDES DE ELIMINACION
  @Override
  public void aceptarSolicitud(Long idSolicitud){
    SolicitudDeEliminacion solicitud= solicitudDeEliminacionRepository.findById(idSolicitud);
    solicitud.setEstadoDeSolicitud(EstadoDeSolicitud.ACEPTADA);
    solicitud.setFechaDeEvaluacion(LocalDateTime.now());
    hechosService.eliminarHecho(solicitud.getIdHecho());

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

     //TODO: FUNCION QUE HACE LA SOLICITUD DE ELIMINACION EN UN OUTPUT
  private SolicitudDeEliminacionOutputDTO SolicitudEliminacionOutputDTO(SolicitudDeEliminacion solicitud) {
    return new SolicitudDeEliminacionOutputDTO();
  }

}