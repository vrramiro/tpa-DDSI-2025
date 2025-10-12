package ar.utn.dssi.FuenteDinamica.error;

public class ErrorGeneralRepositorio extends RuntimeException {
  public ErrorGeneralRepositorio(String mensaje) {
    super(mensaje);
  }
}
