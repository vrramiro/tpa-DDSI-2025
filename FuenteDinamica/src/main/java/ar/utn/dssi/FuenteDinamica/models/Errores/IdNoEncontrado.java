package ar.utn.dssi.FuenteDinamica.models.Errores;

public class IdNoEncontrado extends RuntimeException {
    public IdNoEncontrado(String mensaje) {
      super(mensaje);
    }
}
