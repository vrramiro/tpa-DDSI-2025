package ar.utn.dssi.Agregador.models.entities.algoritmoConsenso;

import ar.utn.dssi.Agregador.models.entities.content.Hecho;

public class multiplesMenciones implements IAlgoritmoDeConsenso{
    @Override
    public Boolean consensuado(Hecho hecho) {
        return  colecciones.stream().filter(coleccion -> coleccion.contieneHecho(hecho))
                .limit(2).count() >= 2
                && colecciones.stream().noneMatch(coleccion -> coleccion.contieneHechoParecido(hecho));
    }

}
