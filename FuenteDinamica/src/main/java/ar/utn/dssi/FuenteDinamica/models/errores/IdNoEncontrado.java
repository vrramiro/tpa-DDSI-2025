package ar.utn.dssi.FuenteDinamica.models.errores;

public class IdNoEncontrado extends RuntimeException {
  public IdNoEncontrado(String mensaje) {
    super(mensaje);
  }
}
