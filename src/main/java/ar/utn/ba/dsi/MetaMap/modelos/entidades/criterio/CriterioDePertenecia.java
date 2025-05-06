package ar.utn.ba.dsi.MetaMap.modelos.entidades.criterio;

import ar.edu.utn.frba.dds.contenido.Hecho;

public interface CriterioDePertenecia {
    public Boolean hechoLoCumple(Hecho unHecho);
}
