package ar.utn.dssi.Estadisticas.models.errores;

public class RepositorioVacio extends RuntimeException {
  public RepositorioVacio(String mensaje) {
    super(mensaje);
  }
}
