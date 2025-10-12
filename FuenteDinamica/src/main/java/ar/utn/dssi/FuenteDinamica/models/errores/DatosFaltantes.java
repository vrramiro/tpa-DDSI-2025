package ar.utn.dssi.FuenteDinamica.models.errores;

public class DatosFaltantes extends RuntimeException {
  public DatosFaltantes(String mensaje) {
    super(mensaje);
  }
}
