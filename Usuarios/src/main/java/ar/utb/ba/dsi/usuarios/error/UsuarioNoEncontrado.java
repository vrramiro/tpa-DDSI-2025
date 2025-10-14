package ar.utb.ba.dsi.usuarios.error;

public class UsuarioNoEncontrado extends RuntimeException {

  public UsuarioNoEncontrado(String nombre) {
    super("No se encontro al usuario: " + nombre);
  }
}
