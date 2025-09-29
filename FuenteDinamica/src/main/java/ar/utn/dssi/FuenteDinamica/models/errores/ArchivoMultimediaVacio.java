package ar.utn.dssi.FuenteDinamica.models.errores;

public class ArchivoMultimediaVacio extends RuntimeException {
    public ArchivoMultimediaVacio(String message) {
        super(message);
    }
}
