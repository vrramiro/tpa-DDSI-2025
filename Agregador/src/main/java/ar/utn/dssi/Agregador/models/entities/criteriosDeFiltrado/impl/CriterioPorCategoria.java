package ar.utn.dssi.Agregador.models.entities.criteriosDeFiltrado.impl;

import ar.utn.dssi.Agregador.models.entities.Categoria;
import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.criteriosDeFiltrado.ICriterioDeFiltrado;

public class CriterioPorCategoria implements ICriterioDeFiltrado {
    private Categoria categoria;

    public CriterioPorCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Boolean loCumple(Hecho unHecho) {
        return unHecho.getCategoria().equals(this.categoria);
    }
}
