package ar.utn.dssi.FuenteProxy.service.impl;

import ar.utn.dssi.FuenteProxy.models.DTOs.output.HechoOutputDTO;
import ar.utn.dssi.FuenteProxy.models.entities.Hecho;
import ar.utn.dssi.FuenteProxy.models.entities.fuentes.Fuente;
import ar.utn.dssi.FuenteProxy.models.entities.fuentes.TipoFuente;
import ar.utn.dssi.FuenteProxy.models.errores.HechoNoEcontrado;
import ar.utn.dssi.FuenteProxy.models.mappers.MapperDeHechos;
import ar.utn.dssi.FuenteProxy.models.entities.normalizador.INormalizadorAdapter;
import ar.utn.dssi.FuenteProxy.models.repositories.IFuenteRepository;
import ar.utn.dssi.FuenteProxy.models.repositories.IHechoRepository;
import ar.utn.dssi.FuenteProxy.service.IHechosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HechosService implements IHechosService {
  @Autowired
  private IHechoRepository hechoRepository;

  @Autowired
  private IFuenteRepository fuenteRepository;

  @Autowired
  private INormalizadorAdapter normalizadorAdapter;

  @Override
  public List<HechoOutputDTO> obtenerHechos(LocalDateTime fechaUltimaComunicacion) {
    if(fechaUltimaComunicacion == null) {
      throw new IllegalArgumentException("Error al cargar la fecha de ultima comunicacion");
    }

    List<Hecho> hechos = hechoRepository.findByEliminadoFalseAndFechaCargaIsAfter(fechaUltimaComunicacion);

    return hechos.stream().map(MapperDeHechos::hechoOutputDTO).collect(Collectors.toList());
  }

  @Override
  public List<HechoOutputDTO> obtenerHechosInstanciasMetamapa() {
    List<Fuente> fuentes = fuenteRepository.findFuentesByTipoFuente(TipoFuente.METAMAPA);
    //TODO: Ver si conviene hacerlo Mono o no.
    return List.of();
  }

  @Override
  public void importarHechos() {
    List<Fuente> fuentes = fuenteRepository.findFuentesByTipoFuenteNot(TipoFuente.METAMAPA);

    List<Hecho> hechos = fuentes.stream().flatMap(fuente -> fuente.importarHechos().block().stream()).toList();

    List<Hecho> hechosNormalizados = new ArrayList<>();

    try {
      for (Hecho hecho : hechos) {
        if(hechoRepository.existsByIdExternoAndFuente(hecho.getIdExterno(), hecho.getFuente())) {
          System.out.println("Ya existe el hecho con idExterno " + hecho.getIdExterno() + " y fuente " + hecho.getFuente().getId());
          continue;
        }

        Hecho hechoNormalizado = normalizadorAdapter.obtenerHechoNormalizado(hecho).block();

        if (hechoNormalizado != null) {
          System.out.println("normalice: " + hechoNormalizado.getCategoria().getNombre());
          hechosNormalizados.add(hechoNormalizado);
        }
      }

      hechoRepository.saveAll(hechosNormalizados);
    } catch (Exception e) {
      System.out.println("Error al normalizar hechos: " + e.getMessage());
    }

    System.out.println("termine de normalizar");
  }

  @Override
  public void eliminarHecho(Long idHecho) {
    Hecho hecho = hechoRepository.findById(idHecho).orElseThrow(() -> new HechoNoEcontrado(idHecho));

    hecho.setEliminado(true);

    hechoRepository.save(hecho);
  }
}


