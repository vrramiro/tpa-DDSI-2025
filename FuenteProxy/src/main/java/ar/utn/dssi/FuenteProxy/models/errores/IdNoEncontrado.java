package ar.utn.dssi.FuenteProxy.models.errores;

public class IdNoEncontrado extends RuntimeException {
    public IdNoEncontrado(String mensaje) {
      super(mensaje);
    }
}
