package ar.utn.dssi.FuenteProxy.dto.output;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ErrorDTO {
  String message;
  String status;
  LocalDateTime timestamp;

  public ErrorDTO(String message, String number, LocalDateTime now) {
    this.message = message;
    this.status = number;
    this.timestamp = now;
  }
}
