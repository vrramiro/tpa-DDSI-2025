package ar.utb.ba.dsi.Normalizador.models.entities.errores;

public class DatosFaltantes extends RuntimeException {
  public DatosFaltantes(String mensaje) {
    super(mensaje);
  }
}
