package ar.utn.dssi.Agregador.models.DTOs.inputDTO;

import ar.utn.dssi.Agregador.models.entities.content.Hecho;
import lombok.Data;

@Data
public class SolicitudDeEliminacionInputDTO {
  private Long idHecho;
  private String descripcion;
}
