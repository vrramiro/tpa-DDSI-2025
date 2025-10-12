package ar.utn.dssi.Agregador.error;

public class ColeccionNoEncontrada extends RuntimeException {
  public ColeccionNoEncontrada(String handle) {
    super("No se pudo obtener la coleccion con id: " + handle);
  }
}
