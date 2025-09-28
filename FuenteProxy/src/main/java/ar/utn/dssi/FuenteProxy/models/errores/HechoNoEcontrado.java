package ar.utn.dssi.FuenteProxy.models.errores;

public class HechoNoEcontrado extends RuntimeException {
  public HechoNoEcontrado(Long idHecho) {
    super("No se encontró el hecho con id: " + idHecho.toString());
  }
}
