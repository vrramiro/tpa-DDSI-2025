package ar.utn.dssi.Agregador.models.entities.criterio.impl;

import ar.utn.dssi.Agregador.models.entities.content.Hecho;
import ar.utn.dssi.Agregador.models.entities.criterio.ICriterioDePertenencia;

import java.time.LocalDate;

public class CriterioPorFecha implements ICriterioDePertenencia {
    private LocalDate fechaDesde;
    private LocalDate fechaHasta;

    public CriterioPorFecha(LocalDate desde,LocalDate hasta) {
        this.fechaDesde = desde;
        this.fechaHasta = hasta;
    }

    public Boolean hechoLoCumple(Hecho unHecho) {
        LocalDate fechaDeAcontecimiento = unHecho.getFechaAcontecimiento().toLocalDate();

        return fechaDeAcontecimiento.isAfter(this.fechaDesde) && fechaDeAcontecimiento.isBefore(this.fechaHasta);
    }
}
