package ar.utn.dssi.Agregador.models.entities.algoritmoConsenso;

import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.fuente.Fuente;


import java.util.List;

public interface IAlgoritmoConsenso {
    public List<Hecho> consensuar(List<Hecho> hechos, List<Fuente> fuentes);
}

