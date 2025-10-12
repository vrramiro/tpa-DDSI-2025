package ar.utb.ba.dsi.Normalizador.models.entities.errores;

public class HechoNoEcontrado extends RuntimeException {
  public HechoNoEcontrado(String mensaje) {
    super(mensaje);
  }
}
