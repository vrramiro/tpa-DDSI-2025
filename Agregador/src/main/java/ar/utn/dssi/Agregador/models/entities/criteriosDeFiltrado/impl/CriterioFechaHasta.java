package ar.utn.dssi.Agregador.models.entities.criteriosDeFiltrado.impl;

import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.criteriosDeFiltrado.ICriterioDeFiltrado;
import java.time.LocalDate;

public class CriterioFechaHasta implements ICriterioDeFiltrado {
  private LocalDate fechaHasta;

  public CriterioFechaHasta(LocalDate fechaHasta) {
    this.fechaHasta = fechaHasta;
  }

  public Boolean loCumple(Hecho unHecho) {
    return unHecho.getFechaCarga().toLocalDate().isBefore(this.fechaHasta);
  }
}
