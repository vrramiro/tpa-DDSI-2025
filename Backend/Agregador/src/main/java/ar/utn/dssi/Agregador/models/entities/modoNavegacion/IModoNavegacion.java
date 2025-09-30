package ar.utn.dssi.Agregador.models.entities.modoNavegacion;

import ar.utn.dssi.Agregador.models.entities.Coleccion;
import ar.utn.dssi.Agregador.models.entities.Hecho;

public interface IModoNavegacion {
    Boolean hechoNavegable(Hecho hecho, Coleccion coleccion);
}
