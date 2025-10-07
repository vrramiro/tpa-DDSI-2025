package ar.utn.dssi.Agregador.services.impl;

import ar.utn.dssi.Agregador.models.DTOs.outputDTO.SolicitudDeEliminacionOutputDTO;
import ar.utn.dssi.Agregador.models.repositories.IHechosRepository;
import ar.utn.dssi.Agregador.models.entities.spam.DetectorDeSpam;
import ar.utn.dssi.Agregador.models.DTOs.inputDTO.SolicitudDeEliminacionInputDTO;
import ar.utn.dssi.Agregador.models.entities.solicitud.EstadoDeSolicitud;
import ar.utn.dssi.Agregador.models.entities.solicitud.SolicitudDeEliminacion;
import ar.utn.dssi.Agregador.models.repositories.ISolicitudDeEliminacionRepository;
import ar.utn.dssi.Agregador.services.IHechosService;
import ar.utn.dssi.Agregador.services.ISolicitudDeEliminacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SolicitudDeEliminacionService implements ISolicitudDeEliminacionService {

  @Autowired
  private ISolicitudDeEliminacionRepository solicitudDeEliminacionRepository;

  @Autowired
  private IHechosService hechosService;

  @Autowired
  private IHechosRepository hechosRepository;

  @Value("${caracteresMinimosSolicitud}")
  private Integer caracteresMinimos;

  //CREAR SOLICITUDES DE ELIMINACION
  @Override
  public SolicitudDeEliminacionOutputDTO crearSolicitudDeEliminacion(SolicitudDeEliminacionInputDTO solicitudDeEliminacion){
    SolicitudDeEliminacion solicitud = new SolicitudDeEliminacion();
    solicitud.setHecho(hechosRepository.findById(solicitudDeEliminacion.getIdHecho())
            .orElseThrow(() -> new NoSuchElementException("No se encontró el hecho con ID: " + solicitudDeEliminacion.getIdHecho())));

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

    return this.solicitudEliminacionOutputDTO(solicitud);
  }

  //ACEPTAR O RECHAZAR SOLICITUDES DE ELIMINACION
  @Override
  public void aceptarSolicitud(Long idSolicitud){
    SolicitudDeEliminacion solicitud= solicitudDeEliminacionRepository.findById(idSolicitud)
            .orElseThrow(() -> new NoSuchElementException("No se encontró la solicitud con ID: " + idSolicitud));
    solicitud.setEstadoDeSolicitud(EstadoDeSolicitud.ACEPTADA);
    solicitud.setFechaDeEvaluacion(LocalDateTime.now());

    hechosService.eliminarHecho(solicitud.getHecho().getId());

    this.solicitudDeEliminacionRepository.save(solicitud);
  }

  @Override
  public void rechazarSolicitud(Long idSolicitud){
       SolicitudDeEliminacion solicitud = this.solicitudDeEliminacionRepository.findById(idSolicitud)
               .orElseThrow(() -> new NoSuchElementException("No se encontró la solicitud con ID: " + idSolicitud));
       solicitud.setEstadoDeSolicitud(EstadoDeSolicitud.RECHAZADA);
       solicitud.setFechaDeEvaluacion(LocalDateTime.now());

       this.solicitudDeEliminacionRepository.save(solicitud);
     }

  @Override
  public List<SolicitudDeEliminacionOutputDTO> obtenerSolicitudes(String tipoEstado) {
    List<SolicitudDeEliminacion> solicitudes;

    //TODO EMBELLECER ESTO
    switch (tipoEstado.toLowerCase()) {
      case "spam":
        solicitudes = this.solicitudDeEliminacionRepository.findByEsSpam(true);
        break;
      case "no-spam":
        solicitudes = this.solicitudDeEliminacionRepository.findByEsSpam(false);
        break;
      case "aceptada":
        solicitudes = this.solicitudDeEliminacionRepository.findByEstadoDeSolicitud(EstadoDeSolicitud.ACEPTADA);
        break;
      case "rechazada":
        solicitudes = this.solicitudDeEliminacionRepository.findByEstadoDeSolicitud(EstadoDeSolicitud.RECHAZADA);
        break;
      case "pendiente":
        solicitudes = this.solicitudDeEliminacionRepository.findByEstadoDeSolicitud(EstadoDeSolicitud.PENDIENTE);
        break;
      case "todos":
      default:
        solicitudes = this.solicitudDeEliminacionRepository.findAll();
        break;
    }

    return solicitudes.stream()
            .map(this::solicitudEliminacionOutputDTO)
            .toList();
  }

  public void setDescripcion(String descripcion, SolicitudDeEliminacion solicitudDeEliminacion) {
       if (descripcion == null || descripcion.length() < caracteresMinimos) {
         throw new IllegalArgumentException("La descripción debe tener minimo " + caracteresMinimos);
       }
       solicitudDeEliminacion.setDescripcion(descripcion);
     }

  private SolicitudDeEliminacionOutputDTO solicitudEliminacionOutputDTO(SolicitudDeEliminacion solicitud) {
    var dtoSolicitudEliminacion = new SolicitudDeEliminacionOutputDTO();

      dtoSolicitudEliminacion.setDescripcion(solicitud.getDescripcion());
      dtoSolicitudEliminacion.setEstadoDeSolicitud(solicitud.getEstadoDeSolicitud());
      dtoSolicitudEliminacion.setFechaDeCreacion(solicitud.getFechaDeCreacion());
      dtoSolicitudEliminacion.setFechaDeEvaluacion(solicitud.getFechaDeEvaluacion());
      dtoSolicitudEliminacion.setEsSpam(solicitud.isEsSpam());

    return dtoSolicitudEliminacion;
  }
}