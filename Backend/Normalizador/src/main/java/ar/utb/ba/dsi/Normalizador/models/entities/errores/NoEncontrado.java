package ar.utb.ba.dsi.Normalizador.models.entities.errores;

public class NoEncontrado extends RuntimeException {
    public NoEncontrado(String mensaje) {
      super(mensaje);
    }
}
