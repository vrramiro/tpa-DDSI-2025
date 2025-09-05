package ar.utn.dssi.Agregador.services;

import ar.utn.dssi.Agregador.models.entities.Hecho;
import java.util.List;

public interface IFuentesService {
  public List<Hecho> hechosNuevos();
  public List<Hecho> hechosMetamapa();
}
