package ar.utn.dssi.Agregador.models.entities.modoNavegacion.impl;

import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.modoNavegacion.IModoNavegacion;

import java.util.List;

public class NavegacionCurada implements IModoNavegacion {

    @Override
    public List<Hecho> hechosNavegables(List<Hecho> hechos) {

    List<Hecho> hechosNavegables =
            hechos.stream()
                    .filter(hecho -> this.hechoEstaCurado(hecho))
                    .toList();

    return hechosNavegables;
    }

    private boolean hechoEstaCurado(Hecho hecho) {
        //TODO: ME FIJO SI ESTA CONSENSUADO Y RETORNO TRUE O FALSE
        return true;
    }
}
