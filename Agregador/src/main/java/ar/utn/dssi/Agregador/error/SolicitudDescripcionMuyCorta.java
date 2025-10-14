package ar.utn.dssi.Agregador.error;

public class SolicitudDescripcionMuyCorta extends RuntimeException {
  public SolicitudDescripcionMuyCorta(String message) {
    super(message);
  }
}
