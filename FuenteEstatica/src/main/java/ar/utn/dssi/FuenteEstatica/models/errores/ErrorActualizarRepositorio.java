package ar.utn.dssi.FuenteEstatica.models.errores;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ErrorActualizarRepositorio extends RuntimeException {
  @Getter
  private final HttpStatus status;

  public ErrorActualizarRepositorio(String mensaje, HttpStatus status) {
    super(mensaje);
    this.status = status;
  }
}
