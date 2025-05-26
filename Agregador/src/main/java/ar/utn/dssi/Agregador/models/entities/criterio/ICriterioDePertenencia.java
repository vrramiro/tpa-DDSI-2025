package ar.utn.dssi.Agregador.models.entities.criterio;

import ar.utn.dssi.Agregador.models.entities.content.Hecho;

public interface ICriterioDePertenencia {
    public Boolean hechoLoCumple(Hecho unHecho);
}
