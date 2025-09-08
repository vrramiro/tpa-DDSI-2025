package ar.utn.dssi.FuenteProxy.service.impl;

import ar.utn.dssi.FuenteProxy.models.DTOs.output.HechoOutputDTO;
import ar.utn.dssi.FuenteProxy.models.entities.Hecho;
import ar.utn.dssi.FuenteProxy.models.entities.fuentes.Fuente;
import ar.utn.dssi.FuenteProxy.models.entities.fuentes.TipoFuente;
import ar.utn.dssi.FuenteProxy.models.mappers.MapperDeHechos;
import ar.utn.dssi.FuenteProxy.models.normalizadorAdapter.INormalizadorAdapter;
import ar.utn.dssi.FuenteProxy.models.repositories.IFuenteRepository;
import ar.utn.dssi.FuenteProxy.models.repositories.IHechoRepository;
import ar.utn.dssi.FuenteProxy.service.IHechosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HechosService implements IHechosService {

  @Autowired
  private IHechoRepository hechoRepository;
  private IFuenteRepository fuenteRepository;
  private INormalizadorAdapter normalizadorAdapter;


  @Override
  public List<HechoOutputDTO> obtenerHechos() {
    return hechoRepository.findAll().stream().map(MapperDeHechos::hechoOutputDTO).collect(Collectors.toList());
  }

  @Override
  public List<HechoOutputDTO> obtenerHechosInstanciasMetamapa() {
    List<Fuente> fuentes = fuenteRepository.findTipo(TipoFuente.METAMAPA);
    //TODO: Ver si conviene hacerlo Mono o no.
    return List.of();
  }


  public void importarHechos() {
    List<Fuente> fuentes = fuenteRepository.findTipoNot(TipoFuente.METAMAPA);
    List<Hecho> hechos = fuentes.stream().flatMap(fuente -> fuente.importarHechos().block().stream()).toList();

    List<Hecho> hechosNormalizados = new ArrayList<>();

    for (Hecho hecho : hechos) {
      try {
        Hecho hechoNormalizado = normalizadorAdapter.obtenerHechoNormalizado(hecho).block();

        if (hechoNormalizado != null) {
          hechosNormalizados.add(hechoNormalizado);
        }
      } catch (Exception e) {
        System.err.println("Error normalizando hecho: " + e.getMessage());
      }
    }

    hechoRepository.saveAll(hechosNormalizados);

  }

}


