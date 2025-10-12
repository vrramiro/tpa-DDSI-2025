package ar.utn.dssi.FuenteDinamica.models.errores;

public class RepositorioVacio extends RuntimeException {
    public RepositorioVacio(String mensaje) {
        super(mensaje);
    }
}
