package ar.utn.dssi.Agregador.models.entities.algoritmoConsenso;

import ar.utn.dssi.Agregador.models.entities.content.Coleccion;
import ar.utn.dssi.Agregador.models.entities.content.Hecho;
import ar.utn.dssi.Agregador.models.repositories.IColeccionRepository;

import java.util.List;

public class absoluta implements IAlgoritmoDeConsenso{
    @Override
    public Boolean consensuado(Hecho hecho) {
       return colecciones.stream().allMatch(coleccion -> coleccion.contieneHecho(hecho));
    }

}

