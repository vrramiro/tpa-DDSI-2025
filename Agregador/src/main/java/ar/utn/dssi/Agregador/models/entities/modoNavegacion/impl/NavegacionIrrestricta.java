package ar.utn.dssi.Agregador.models.entities.modoNavegacion.impl;

import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.modoNavegacion.IModoNavegacion;

import java.util.List;

public class NavegacionIrrestricta implements IModoNavegacion {
    @Override
    public List<Hecho> hechosNavegables(List<Hecho> hechos) {
        return hechos;
    }
}
