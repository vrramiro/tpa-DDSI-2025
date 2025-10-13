package ar.utb.ba.dsi.usuarios.dto.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorDTO {
  private String mensaje;
  private String status;
  private LocalDateTime fecha;
}
