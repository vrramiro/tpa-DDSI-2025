package ar.utb.ba.dsi.modelos.entidades.contenido;

import lombok.Getter;
import lombok.Setter;
@Getter

public class Ubicacion {
    private double latitud;
    private double longitud;
    @Setter private String lugar;

    public Ubicacion(double latitud, double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Ubicacion(String lugar, double latitud, double longitud) {
        this.lugar = lugar;
        this.latitud = latitud;
        this.longitud = longitud;
    }

}
