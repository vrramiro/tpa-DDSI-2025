package ar.utb.ba.dsi.Normalizador.models.entities.errores;


public class CategoriaNotFoundException extends RuntimeException {
    public CategoriaNotFoundException(String mensaje) {
        super(mensaje);
    }
}
