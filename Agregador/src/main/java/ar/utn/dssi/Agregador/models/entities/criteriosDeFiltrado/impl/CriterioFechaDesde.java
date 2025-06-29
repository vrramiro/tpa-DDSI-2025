package ar.utn.dssi.Agregador.models.entities.criteriosDeFiltrado.impl;

import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.criteriosDeFiltrado.ICriterioDeFiltrado;
import java.time.LocalDate;

public class CriterioFechaDesde implements ICriterioDeFiltrado {
  private LocalDate fechaDesde;

  public CriterioFechaDesde(LocalDate fechaDesde) {
    this.fechaDesde = fechaDesde;
  }

  public Boolean loCumple(Hecho unHecho) {
    return unHecho.getFechaCarga().toLocalDate().isAfter(this.fechaDesde);
  }
}