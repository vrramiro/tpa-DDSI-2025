package ar.utn.dssi.FuenteDinamica.models.errores;

public class DirectorioNoCreado extends RuntimeException {
  public DirectorioNoCreado(String message) {
    super(message);
  }
}
