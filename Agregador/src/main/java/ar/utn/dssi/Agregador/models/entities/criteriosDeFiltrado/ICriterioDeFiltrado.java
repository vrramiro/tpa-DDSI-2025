package ar.utn.dssi.Agregador.models.entities.criteriosDeFiltrado;

import ar.utn.dssi.Agregador.models.entities.Hecho;

public interface ICriterioDeFiltrado {
    public Boolean loCumple(Hecho unHecho);
}
