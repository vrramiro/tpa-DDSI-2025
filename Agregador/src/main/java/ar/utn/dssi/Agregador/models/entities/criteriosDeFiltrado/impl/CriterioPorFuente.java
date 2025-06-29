package ar.utn.dssi.Agregador.models.entities.criteriosDeFiltrado.impl;

import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.criteriosDeFiltrado.ICriterioDeFiltrado;

public class CriterioPorFuente implements ICriterioDeFiltrado {
    Long idFuente;

    public CriterioPorFuente(Long idFuenteOrigen) {
        this.idFuente = idFuenteOrigen;
    }

    public Boolean loCumple(Hecho unHecho) {
        return unHecho.getIdFuente().equals(idFuente);
    }
}