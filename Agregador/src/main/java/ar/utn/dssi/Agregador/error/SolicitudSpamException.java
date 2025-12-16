package ar.utn.dssi.Agregador.error;

public class SolicitudSpamException extends RuntimeException {
    public SolicitudSpamException(String message) {
        super(message);
    }
}