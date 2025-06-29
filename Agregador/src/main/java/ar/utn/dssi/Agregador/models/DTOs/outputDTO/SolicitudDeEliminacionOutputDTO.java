package ar.utn.dssi.Agregador.models.DTOs.outputDTO;

import ar.utn.dssi.Agregador.models.entities.solicitud.EstadoDeSolicitud;
import java.time.LocalDateTime;

public class SolicitudDeEliminacionOutputDTO {
  private String descripcion;
  private EstadoDeSolicitud estadoDeSolicitud;
  private LocalDateTime fechaDeCreacion;
  private LocalDateTime fechaDeEvaluacion;
  private boolean esSpam;
}
