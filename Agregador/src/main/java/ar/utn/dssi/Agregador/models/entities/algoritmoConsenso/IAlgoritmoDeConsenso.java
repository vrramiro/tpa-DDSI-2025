package ar.utn.dssi.Agregador.models.entities.algoritmoConsenso;

import ar.utn.dssi.Agregador.models.entities.Coleccion;
import ar.utn.dssi.Agregador.models.entities.Hecho;
import java.util.List;

public interface IAlgoritmoDeConsenso {

    public default List<Hecho> consensuar();
}

