package ar.utn.dssi.Estadisticas.models.DTOs.inputs;

import lombok.Data;
import java.util.List;

@Data
public class ColeccionInputDTO {
  private Long id;
  private String titulo;
  private List<HechoInputDTO> hechos;
}
