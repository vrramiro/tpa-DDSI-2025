package ar.utn.dssi.FuenteDinamica.error;

public class RepositorioVacio extends RuntimeException {
  public RepositorioVacio(String mensaje) {
    super(mensaje);
  }
}
