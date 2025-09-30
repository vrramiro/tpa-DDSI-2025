package ar.utn.dssi.FuenteProxy.models.Errores;

public class ErrorGeneralRepositorio extends RuntimeException {
    public ErrorGeneralRepositorio(String mensaje) {
        super(mensaje);
    }
}
