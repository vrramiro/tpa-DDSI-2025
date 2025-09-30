package ar.utb.ba.dsi.Normalizador.models.entities.errores;

public class ErrorGeneralRepositorio extends RuntimeException {
    public ErrorGeneralRepositorio(String mensaje) {
        super(mensaje);
    }
}
