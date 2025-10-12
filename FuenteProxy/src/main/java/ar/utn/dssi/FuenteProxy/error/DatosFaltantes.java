package ar.utn.dssi.FuenteProxy.error;

public class DatosFaltantes extends RuntimeException {
  public DatosFaltantes(String mensaje) {
    super(mensaje);
  }
}
