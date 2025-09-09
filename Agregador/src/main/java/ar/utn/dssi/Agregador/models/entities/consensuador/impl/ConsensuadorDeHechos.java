package ar.utn.dssi.Agregador.models.entities.consensuador.impl;

import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.algoritmoConsenso.IAlgoritmoConsenso;
import ar.utn.dssi.Agregador.models.entities.consensuador.IConsensuadorDeHechos;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ConsensuadorDeHechos implements IConsensuadorDeHechos {
  private final List<IAlgoritmoConsenso> algoritmosConsenso;

  public ConsensuadorDeHechos(List<IAlgoritmoConsenso> algoritmosConsenso) {
    this.algoritmosConsenso = algoritmosConsenso;
  }

  public void consensuar(List<Hecho> hechos) {
    this.algoritmosConsenso.forEach(algoritmoConsenso -> {
      algoritmoConsenso.consensuar(hechos);
    });
  }
}
