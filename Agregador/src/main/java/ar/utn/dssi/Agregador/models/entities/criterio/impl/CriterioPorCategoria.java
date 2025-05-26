package ar.utn.dssi.Agregador.models.entities.criterio.impl;

import ar.utn.dssi.Agregador.models.entities.content.Categoria;
import ar.utn.dssi.Agregador.models.entities.content.Hecho;
import ar.utn.dssi.Agregador.models.entities.criterio.ICriterioDePertenencia;

public class CriterioPorCategoria implements ICriterioDePertenencia {
    private Categoria categoria;

    public CriterioPorCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Boolean hechoLoCumple(Hecho unHecho) {
        Categoria categoriaDelHecho = unHecho.getCategoria();

        return categoriaDelHecho.equals(this.categoria);
    }
}
