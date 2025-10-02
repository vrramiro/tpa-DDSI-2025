package ar.utn.dssi.FuenteProxy.error;

public class RepositorioVacio extends RuntimeException {
    public RepositorioVacio(String mensaje) {
        super(mensaje);
    }
}
