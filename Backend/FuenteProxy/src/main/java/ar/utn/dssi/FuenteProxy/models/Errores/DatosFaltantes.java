package ar.utn.dssi.FuenteProxy.models.Errores;

public class DatosFaltantes extends RuntimeException{
    public DatosFaltantes(String mensaje) {
        super(mensaje);
    }
}
