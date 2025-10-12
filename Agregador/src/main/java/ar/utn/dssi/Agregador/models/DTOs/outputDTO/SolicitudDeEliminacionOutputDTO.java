package ar.utn.dssi.Agregador.models.DTOs.outputDTO;

import ar.utn.dssi.Agregador.models.entities.solicitud.EstadoDeSolicitud;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SolicitudDeEliminacionOutputDTO {
  private String descripcion;
  private EstadoDeSolicitud estadoDeSolicitud;
  private LocalDateTime fechaDeCreacion;
  private LocalDateTime fechaDeEvaluacion;
  private boolean esSpam;
}
