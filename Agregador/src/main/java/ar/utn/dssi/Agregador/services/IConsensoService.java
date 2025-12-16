package ar.utn.dssi.Agregador.services;

import ar.utn.dssi.Agregador.models.entities.Hecho;
import java.util.List;

public interface IConsensoService {
  void consensuarHechos();

  void aplicarClavesDeComparacion(List<Hecho> hechosImportados);
}
