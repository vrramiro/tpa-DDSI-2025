package ar.utn.dssi.FuenteEstatica.error;

public class RepositorioVacio extends RuntimeException {
  public RepositorioVacio(String mensaje) {
    super(mensaje);
  }
}
