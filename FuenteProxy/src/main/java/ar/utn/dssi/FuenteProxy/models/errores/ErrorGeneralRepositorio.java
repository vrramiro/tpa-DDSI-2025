package ar.utn.dssi.FuenteProxy.models.errores;

public class ErrorGeneralRepositorio extends RuntimeException {
    public ErrorGeneralRepositorio(String mensaje) {
        super(mensaje);
    }
}
