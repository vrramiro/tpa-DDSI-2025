package ar.utn.dssi.Agregador.modelos.entidades.criterio.impl;

import ar.utn.dssi.Agregador.modelos.entidades.contenido.Hecho;
import ar.utn.dssi.Agregador.modelos.entidades.criterio.CriterioDePertenencia;

import java.time.LocalDate;

public class CriterioPorFecha implements CriterioDePertenencia {
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
