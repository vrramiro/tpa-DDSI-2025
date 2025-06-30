package ar.utn.dssi.Agregador.models.entities.modoNavegacion.impl;

import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.algoritmoConsenso.IAlgoritmoDeConsenso;
import ar.utn.dssi.Agregador.models.entities.modoNavegacion.IModoNavegacion;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NavegacionIrrestricta implements IModoNavegacion {
    @Override
    public Boolean hechoNavegable(Hecho hecho, IAlgoritmoDeConsenso algoritmoDeConsenso) {
        return true;
    }
}
