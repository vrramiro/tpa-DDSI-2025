package ar.utn.dssi.Agregador.models.entities.criterio.impl;

import ar.utn.dssi.Agregador.models.entities.content.Hecho;
import ar.utn.dssi.Agregador.models.entities.criterio.ICriterioDePertenencia;

import java.time.LocalDate;

public class CriterioPorFuente implements ICriterioDePertenencia {
    Long idFuente;

    public CriterioPorFuente(Long idFuenteOrigen) {
        this.idFuente = idFuenteOrigen;
    }

    public Boolean hechoLoCumple(Hecho unHecho) {
        return unHecho.getIdFuente().equals(idFuente);
    }
}