package ar.edu.utn.frba.dds.contenido;

public class Ubicacion {
    private double latitud;
    private double longitud;
    private String lugar;

    public Ubicacion(String lugar, double latitud, double longitud) {
        this.lugar = lugar;
        this.latitud = latitud;
        this.longitud = longitud;
    }

}
