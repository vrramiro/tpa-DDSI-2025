package ar.utn.dssi.Agregador.models.entities.consensuador;

import ar.utn.dssi.Agregador.models.entities.Hecho;
import java.util.List;

public interface IConsensuadorDeHechos {
  void consuar(List<Hecho> hechos);
}
