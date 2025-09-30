package ar.utn.dssi.FuenteProxy.models.Errores;

public class RepositorioVacio extends RuntimeException {
    public RepositorioVacio(String mensaje) {
        super(mensaje);
    }
}
