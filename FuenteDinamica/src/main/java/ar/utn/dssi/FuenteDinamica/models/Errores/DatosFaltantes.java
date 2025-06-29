package ar.utn.dssi.FuenteDinamica.models.Errores;

public class DatosFaltantes extends RuntimeException{
    public DatosFaltantes(String mensaje) {
        super(mensaje);
    }
}
