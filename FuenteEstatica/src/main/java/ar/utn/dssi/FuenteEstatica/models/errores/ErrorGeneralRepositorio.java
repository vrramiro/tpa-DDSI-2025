package ar.utn.dssi.FuenteEstatica.models.errores;

public class ErrorGeneralRepositorio extends RuntimeException {
    public ErrorGeneralRepositorio(String mensaje) {
        super(mensaje);
    }
}
