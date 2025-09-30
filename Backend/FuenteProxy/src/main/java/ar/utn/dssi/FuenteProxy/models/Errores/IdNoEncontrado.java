package ar.utn.dssi.FuenteProxy.models.Errores;

public class IdNoEncontrado extends RuntimeException {
    public IdNoEncontrado(String mensaje) {
      super(mensaje);
    }
}
