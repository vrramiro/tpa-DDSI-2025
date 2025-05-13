package ar.utn.ba.dsi.MetaMap.fuenteEstatica.modelo.entidades;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Etiqueta {
    private String nombre;

    public Etiqueta(String nombre) {
        this.nombre = nombre;
    }
}

