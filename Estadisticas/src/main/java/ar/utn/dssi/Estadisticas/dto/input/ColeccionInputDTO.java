package ar.utn.dssi.Estadisticas.dto.input;

import lombok.Data;
import java.util.List;

@Data
public class ColeccionInputDTO {
  private Long id;
  private String titulo;
  private List<HechoInputDTO> hechos;
}
