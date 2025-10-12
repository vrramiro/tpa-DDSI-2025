package ar.utn.dssi.FuenteProxy.error;

public class IdNoEncontrado extends RuntimeException {
    public IdNoEncontrado(String mensaje) {
      super(mensaje);
    }
}
