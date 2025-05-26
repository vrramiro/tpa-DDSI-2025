package ar.utn.dssi.Agregador.modelos.entidades.criterio.impl;

import ar.utn.dssi.Agregador.modelos.entidades.contenido.Categoria;
import ar.utn.dssi.Agregador.modelos.entidades.contenido.Hecho;
import ar.utn.dssi.Agregador.modelos.entidades.criterio.CriterioDePertenencia;

public class CriterioPorCategoria implements CriterioDePertenencia {
    private Categoria categoria;

    public CriterioPorCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Boolean hechoLoCumple(Hecho unHecho) {
        Categoria categoriaDelHecho = unHecho.getCategoria();

        return categoriaDelHecho.equals(this.categoria);
    }
}
