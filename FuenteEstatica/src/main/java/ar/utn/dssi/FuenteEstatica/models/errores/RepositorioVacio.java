package ar.utn.dssi.FuenteEstatica.models.errores;

public class RepositorioVacio extends RuntimeException {
    public RepositorioVacio(String mensaje) {
        super(mensaje);
    }
}
