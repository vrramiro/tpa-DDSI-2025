package ar.utn.dssi.Agregador.models.entities.criteriosDeFiltrado.impl;

import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.Ubicacion;

public class CriterioUbicacion {
  private Ubicacion ubicacion;

  public CriterioUbicacion(Ubicacion ubicacion) {
    this.ubicacion = ubicacion;
  }

  public Boolean loCumple(Hecho unHecho) {
    return unHecho.getUbicacion().equals(ubicacion);
  }
}
