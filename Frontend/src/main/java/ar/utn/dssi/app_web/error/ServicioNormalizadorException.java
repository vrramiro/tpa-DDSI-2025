package ar.utn.dssi.app_web.error;

public class ServicioNormalizadorException extends RuntimeException {

  public ServicioNormalizadorException(String message) {
    super(message);
  }

  public ServicioNormalizadorException(String message, Throwable cause) {
    super(message, cause);
  }
}
