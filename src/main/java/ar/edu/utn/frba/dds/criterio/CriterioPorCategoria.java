package ar.edu.utn.frba.dds.criterio;

import ar.edu.utn.frba.dds.contenido.Hecho;

public class CriterioPorCategoria implements CriterioDePertenecia {
    private Categoria categoria;

    public CriterioPorCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Boolean hechoLoCumple(Hecho unHecho) {
        Categoria categoriaDelHecho = unHecho.getCategoria();

        return categoriaDelHecho.equals(this.categoria);
    }
}
