package ar.utn.dssi.Estadisticas.dto.input;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class HechoInputDTO {
  private String categoria;
  private UbicacionInputDTO ubicacion;
  private LocalDateTime fechaAcontecimiento;
}
