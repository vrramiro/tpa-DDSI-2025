package ar.utn.dssi.Agregador.error;

public class DatosDeColeccionFaltantes extends RuntimeException {
  public DatosDeColeccionFaltantes(String message) {
    super(message);
  }
}
