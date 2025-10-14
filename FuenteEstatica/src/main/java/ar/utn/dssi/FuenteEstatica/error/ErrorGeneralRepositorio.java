package ar.utn.dssi.FuenteEstatica.error;

public class ErrorGeneralRepositorio extends RuntimeException {
  public ErrorGeneralRepositorio(String mensaje) {
    super(mensaje);
  }
}
