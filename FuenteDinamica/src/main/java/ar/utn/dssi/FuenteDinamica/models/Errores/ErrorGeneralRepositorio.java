package ar.utn.dssi.FuenteDinamica.models.Errores;

public class ErrorGeneralRepositorio extends RuntimeException {
    public ErrorGeneralRepositorio(String mensaje) {
        super(mensaje);
    }
}
