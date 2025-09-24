package ar.utb.ba.dsi.Normalizador.models.entities.errores;

public class RepositorioVacio extends RuntimeException {
    public RepositorioVacio(String mensaje) {
        super(mensaje);
    }
}
