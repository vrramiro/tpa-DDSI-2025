package ar.utn.dssi.Agregador.models.entities.algoritmoConsenso;

import ar.utn.dssi.Agregador.models.entities.Hecho;
import java.util.List;

public interface IAlgoritmoDeConsenso {
    Boolean consensuar(List<Hecho> hechosDelAgregador, List<Hecho> hechosDeColeccion);
}

