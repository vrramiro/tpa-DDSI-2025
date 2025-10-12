package ar.utb.ba.dsi.usuarios.error;

public class UsuarioDatosFaltantes extends RuntimeException {
  public UsuarioDatosFaltantes(String message) {
    super(message);
  }
}
