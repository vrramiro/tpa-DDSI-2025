package ar.utn.dssi.Agregador.models.entities.algoritmoConsenso;

import ar.utn.dssi.Agregador.models.entities.Hecho;

public class mayoriaSimple implements IAlgoritmoDeConsenso{
    private long cantidadDeColecciones = colecciones.size();

    @Override
    public Boolean consensuado(Hecho hecho) {
        long contienenHecho = colecciones.stream().filter(coleccione -> coleccione.contieneHecho(hecho)).count();

        return contienenHecho >= (cantidadDeColecciones/2);
    }
}

