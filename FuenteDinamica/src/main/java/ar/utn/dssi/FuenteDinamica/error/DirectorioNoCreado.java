package ar.utn.dssi.FuenteDinamica.error;

public class DirectorioNoCreado extends RuntimeException {
  public DirectorioNoCreado(String message) {
    super(message);
  }
}
