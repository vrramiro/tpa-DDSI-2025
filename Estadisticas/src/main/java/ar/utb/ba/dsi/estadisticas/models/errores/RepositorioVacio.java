package ar.utb.ba.dsi.estadisticas.models.errores;

public class RepositorioVacio extends RuntimeException {
    public RepositorioVacio(String mensaje) { super(mensaje);}
}
