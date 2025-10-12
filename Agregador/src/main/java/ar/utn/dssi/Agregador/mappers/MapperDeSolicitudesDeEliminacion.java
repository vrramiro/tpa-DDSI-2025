package ar.utn.dssi.Agregador.mappers;

import ar.utn.dssi.Agregador.dto.output.SolicitudDeEliminacionOutputDTO;
import ar.utn.dssi.Agregador.models.entities.solicitud.EstadoDeSolicitud;
import ar.utn.dssi.Agregador.models.entities.solicitud.SolicitudDeEliminacion;

public class MapperDeSolicitudesDeEliminacion {
  public static EstadoDeSolicitud estadoDeSolicitudFromString(String estado) {
    String estadoNormalizado = estado.trim().toUpperCase();

    try {
      return EstadoDeSolicitud.valueOf(estadoNormalizado);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Estado de solicitud inválido: " + estado);
    }
  }

  public static SolicitudDeEliminacionOutputDTO outpuDTOFromSolicitudDeEliminacion(SolicitudDeEliminacion solicitud) {
    var dtoSolicitudEliminacion = new SolicitudDeEliminacionOutputDTO();

    dtoSolicitudEliminacion.setIdSolicitud(solicitud.getIdSolicitud());
    dtoSolicitudEliminacion.setDescripcion(solicitud.getDescripcion());
    dtoSolicitudEliminacion.setEstadoDeSolicitud(solicitud.getEstadoDeSolicitud());
    dtoSolicitudEliminacion.setFechaDeCreacion(solicitud.getFechaDeCreacion());
    dtoSolicitudEliminacion.setFechaDeEvaluacion(solicitud.getFechaDeEvaluacion());
    dtoSolicitudEliminacion.setEsSpam(solicitud.isEsSpam());
    dtoSolicitudEliminacion.setHecho(MapperDeHechos.hechoToOutputDTO(solicitud.getHecho()));

    return dtoSolicitudEliminacion;
  }
}
