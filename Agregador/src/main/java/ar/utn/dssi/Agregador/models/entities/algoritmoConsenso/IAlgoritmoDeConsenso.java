package ar.utn.dssi.Agregador.models.entities.algoritmoConsenso;

import ar.utn.dssi.Agregador.models.entities.Coleccion;
import ar.utn.dssi.Agregador.models.entities.Hecho;
import java.util.List;

public interface IAlgoritmoDeConsenso {

    public default List<Hecho> consensuar(List<Hecho> hechos, List<Long> idsFuentes) {
        return hechos
            .stream()
            .filter(hecho -> cumpleAlgoritmo(hechos, hecho, idsFuentes))
            .toList();
    }

    public default boolean cumpleAlgoritmo(List<Hecho> hechos, Hecho hecho, List<Long> idsFuentes){}

}

