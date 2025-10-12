package ar.utn.dssi.Agregador.models.entities.modoNavegacion.impl;

import ar.utn.dssi.Agregador.models.entities.Coleccion;
import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.modoNavegacion.IModoNavegacion;
import org.springframework.stereotype.Component;

@Component
public class NavegacionIrrestricta implements IModoNavegacion {
  @Override
  public Boolean hechoNavegable(Hecho hecho, Coleccion coleccion) {
    return true;
  }
}
