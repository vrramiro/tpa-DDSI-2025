package ar.utn.dssi.Agregador.services.impl;

import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.fuente.ITipoProxy;
import ar.utn.dssi.Agregador.models.entities.fuente.impl.fuenteProxy.FuenteProxy;
import ar.utn.dssi.Agregador.models.repositories.IFuenteRepository;

import ar.utn.dssi.Agregador.services.IFuentesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FuentesService implements IFuentesService {
  @Autowired
  private IFuenteRepository fuenteRepository;

  @Override
  public List<Hecho> hechosNuevos() {
    return this.fuenteRepository.findAll().stream()
        .flatMap(fuente -> fuente.getTipoFuente().hechosNuevos(fuente).stream())

        .toList();
  }

  @Override
  public List<Hecho> hechosMetamapa() {
    return this.fuenteRepository.findByTipoFuente(new FuenteProxy()).stream()
        .flatMap(fuente -> ((ITipoProxy) fuente.getTipoFuente()).hechosMetamapa(fuente).stream())

        .toList();
  }
}
