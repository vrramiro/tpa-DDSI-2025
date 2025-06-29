package ar.utn.dssi.Agregador.models.entities.algoritmoConsenso;

import ar.utn.dssi.Agregador.models.entities.Hecho;

public class absoluta implements IAlgoritmoDeConsenso{
    @Override
    public Boolean consensuado(Hecho hecho) {
       return colecciones.stream().allMatch(coleccion -> coleccion.contieneHecho(hecho));
    }

}

