package ar.utn.dssi.FuenteEstatica.models.errores;

public class ValidacionException extends RuntimeException{
    public ValidacionException(String mensaje) {
        super(mensaje);
    }
}

