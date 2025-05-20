package ar.utn.dssi.Agregador.modelos.entidades.criterio;

import ar.utn.dssi.Agregador.modelos.entidades.contenido.Hecho;

public interface CriterioDePertenencia {
    public Boolean hechoLoCumple(Hecho unHecho);
}
