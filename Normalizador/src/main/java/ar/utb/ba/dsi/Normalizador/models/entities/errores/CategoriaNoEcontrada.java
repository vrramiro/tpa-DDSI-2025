package ar.utb.ba.dsi.Normalizador.models.entities.errores;


public class CategoriaNoEcontrada extends RuntimeException {
    public CategoriaNoEcontrada(String mensaje) {
        super(mensaje);
    }
}
