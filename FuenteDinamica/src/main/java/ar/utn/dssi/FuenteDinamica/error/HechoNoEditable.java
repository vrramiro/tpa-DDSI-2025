package ar.utn.dssi.FuenteDinamica.error;

public class HechoNoEditable extends RuntimeException {
  public HechoNoEditable(String message) {
    super(message);
  }
}
