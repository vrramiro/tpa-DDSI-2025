package ar.utn.dssi.Agregador.models.entities.modoNavegacion.impl;

import ar.utn.dssi.Agregador.models.entities.Coleccion;
import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.modoNavegacion.IModoNavegacion;
import ar.utn.dssi.Agregador.models.repositories.IColeccionRepository;
import ar.utn.dssi.Agregador.models.repositories.impl.ColeccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NavegacionCurada implements IModoNavegacion {
    @Override
    public Boolean hechoNavegable(Hecho hecho, Coleccion coleccion) {
        Boolean navegable = coleccion.getHechosConsensuados().contains(hecho.getId());
        return navegable;
    }
}
