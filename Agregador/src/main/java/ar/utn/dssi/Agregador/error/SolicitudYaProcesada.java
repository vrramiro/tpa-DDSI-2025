package ar.utn.dssi.Agregador.error;

public class SolicitudYaProcesada extends RuntimeException {
  public SolicitudYaProcesada(String message) {
    super(message);
  }
}
