package ar.utn.dssi.FuenteProxy.models.errores;

public class DatosFaltantes extends RuntimeException{
    public DatosFaltantes(String mensaje) {
        super(mensaje);
    }
}
