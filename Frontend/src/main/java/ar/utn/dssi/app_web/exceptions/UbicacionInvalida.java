package ar.utn.dssi.app_web.exceptions;

public class UbicacionInvalida extends RuntimeException {
  public UbicacionInvalida() {
    super("La ubicacion es invalida, debe ser una ubicacion en Argentina.");
  }
}
