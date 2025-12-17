package ar.utn.dssi.Agregador.services.impl;

import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.consensuador.IConsensuadorDeHechos;
import ar.utn.dssi.Agregador.models.entities.fuente.Fuente;
import ar.utn.dssi.Agregador.models.repositories.IFuenteRepository;
import ar.utn.dssi.Agregador.models.repositories.IHechosRepository;
import ar.utn.dssi.Agregador.services.IConsensoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class ConsensoService implements IConsensoService {
  private final IConsensuadorDeHechos consensuadorDeHechos;
  private final IHechosRepository hechosRepository;
  private final IFuenteRepository fuentesRepository;

  public ConsensoService(IHechosRepository hechosRepository, IConsensuadorDeHechos consensuadorDeHechos, IFuenteRepository fuentesRepository) {
    this.hechosRepository = hechosRepository;
    this.consensuadorDeHechos = consensuadorDeHechos;
    this.fuentesRepository = fuentesRepository;
  }

  @Override
  public void consensuarHechos() {
    try {
      List<Hecho> hechos = this.hechosRepository.findByVisible(true);
      hechos.stream().forEach(hecho -> log.info("Consensuando hecho: {}", hecho.getId()));
      List<Fuente> fuentes = this.fuentesRepository.findAll();
      this.consensuadorDeHechos.consensuar(hechos, fuentes);
      hechos.stream().forEach(h -> h.getConsensosDados().stream().forEach(consensoDado -> log.info("Hecho ID: {}, Hecho clave gtupal: {}, Consenso: {}", h.getId(), h.getClaveComparacion(), consensoDado.name())));
      this.hechosRepository.saveAll(hechos);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void inicializarParaConsensuado(List<Hecho> hechosImportados) {
    hechosImportados.forEach(consensuadorDeHechos::inicializarParaConsensuado);
  }
}
