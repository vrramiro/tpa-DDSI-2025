package ar.utn.dssi.Estadisticas.errores;

public class RepositorioVacio extends RuntimeException {
  public RepositorioVacio(String mensaje) {
    super(mensaje);
  }
}
