package ar.utn.dssi.Agregador.models.entities.modoNavegacion.impl;

import ar.utn.dssi.Agregador.models.entities.Coleccion;
import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.modoNavegacion.IModoNavegacion;
import org.springframework.stereotype.Component;

@Component
public class NavegacionCurada implements IModoNavegacion {
    @Override
    public Boolean hechoNavegable(Hecho hecho, Coleccion coleccion) {
        Boolean navegable = true;
                //coleccion.getHechosConsensuados().contains(hecho.getId()); //TODO: REVISAR CONSENSO
        return navegable;
    }
}
