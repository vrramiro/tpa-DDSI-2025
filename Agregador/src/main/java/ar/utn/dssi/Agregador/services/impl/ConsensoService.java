package ar.utn.dssi.Agregador.services.impl;

import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.consensuador.IConsensuadorDeHechos;
import ar.utn.dssi.Agregador.models.repositories.IHechosRepository;
import ar.utn.dssi.Agregador.services.IConsensoService;
import java.util.List;

public class ConsensoService implements IConsensoService {
  private final IConsensuadorDeHechos consensuadorDeHechos;
  private final IHechosRepository hechosRepository;

  public ConsensoService(IHechosRepository hechosRepository, IConsensuadorDeHechos consensuadorDeHechos) {
    this.hechosRepository = hechosRepository;
    this.consensuadorDeHechos = consensuadorDeHechos;
  }

  @Override
  public void consensuarHechos() {
    try {
      List<Hecho> hechos = this.hechosRepository.findAll();
      this.consensuadorDeHechos.consensuar(hechos);
      this.hechosRepository.saveAll(hechos);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
