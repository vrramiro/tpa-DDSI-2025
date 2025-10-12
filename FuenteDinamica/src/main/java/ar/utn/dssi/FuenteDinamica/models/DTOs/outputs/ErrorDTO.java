package ar.utn.dssi.FuenteDinamica.models.DTOs.outputs;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
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
