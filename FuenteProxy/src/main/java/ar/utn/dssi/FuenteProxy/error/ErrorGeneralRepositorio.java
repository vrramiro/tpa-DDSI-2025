package ar.utn.dssi.FuenteProxy.error;

public class ErrorGeneralRepositorio extends RuntimeException {
  public ErrorGeneralRepositorio(String mensaje) {
    super(mensaje);
  }
}
