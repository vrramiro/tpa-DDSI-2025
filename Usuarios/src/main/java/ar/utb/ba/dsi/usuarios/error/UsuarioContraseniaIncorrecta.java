package ar.utb.ba.dsi.usuarios.error;

public class UsuarioContraseniaIncorrecta extends RuntimeException {
  public UsuarioContraseniaIncorrecta(String message) {
    super(message);
  }
}
