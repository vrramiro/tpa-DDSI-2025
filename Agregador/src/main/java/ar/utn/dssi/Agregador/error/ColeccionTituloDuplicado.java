package ar.utn.dssi.Agregador.error;

public class ColeccionTituloDuplicado extends RuntimeException {
  public ColeccionTituloDuplicado(String message) {
    super("Ya existe una coleccion con el titulo: " + message);
  }
}
