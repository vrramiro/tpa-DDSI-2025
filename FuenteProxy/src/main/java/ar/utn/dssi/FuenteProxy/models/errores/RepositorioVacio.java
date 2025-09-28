package ar.utn.dssi.FuenteProxy.models.errores;

public class RepositorioVacio extends RuntimeException {
    public RepositorioVacio(String mensaje) {
        super(mensaje);
    }
}
