package ar.utn.dssi.FuenteDinamica.models.errores;

public class HechoNoEditable extends RuntimeException {
    public HechoNoEditable(String message) {
        super(message);
    }
}
