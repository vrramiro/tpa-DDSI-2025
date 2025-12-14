package ar.utn.dssi.Estadisticas.dto.input;

import lombok.Data;
import java.util.List;

@Data
public class ColeccionInputDTO {
  private String handle;
  private String titulo;
  private List<HechoInputDTO> hechos;
}
