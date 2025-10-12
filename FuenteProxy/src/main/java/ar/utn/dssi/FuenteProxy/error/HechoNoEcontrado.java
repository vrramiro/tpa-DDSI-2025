package ar.utn.dssi.FuenteProxy.error;

public class HechoNoEcontrado extends RuntimeException {
  public HechoNoEcontrado(Long idHecho) {
    super("No se encontró el hecho con id: " + idHecho.toString());
  }
}
