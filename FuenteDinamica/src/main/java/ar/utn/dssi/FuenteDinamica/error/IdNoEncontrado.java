package ar.utn.dssi.FuenteDinamica.error;

public class IdNoEncontrado extends RuntimeException {
  public IdNoEncontrado(String mensaje) {
    super(mensaje);
  }
}
