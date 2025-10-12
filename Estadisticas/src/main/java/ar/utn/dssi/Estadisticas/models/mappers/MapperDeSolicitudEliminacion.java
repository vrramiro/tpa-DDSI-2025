package ar.utn.dssi.Estadisticas.models.mappers;

import ar.utn.dssi.Estadisticas.models.DTOs.inputs.SolicitudDeEliminacionInputDTO;
import ar.utn.dssi.Estadisticas.models.entities.SolicitudDeEliminacion;

public class MapperDeSolicitudEliminacion {
  public static SolicitudDeEliminacion solicitudFromInput(SolicitudDeEliminacionInputDTO solicitud) {
    SolicitudDeEliminacion sol = new SolicitudDeEliminacion();
    sol.setId(solicitud.getId());
    sol.setEsSpam(solicitud.getEsSpam());
    return sol;
  }
}
