package ar.utn.dssi.FuenteDinamica.error;

public class DatosFaltantes extends RuntimeException {
  public DatosFaltantes(String mensaje) {
    super(mensaje);
  }
}
