package ar.utn.dssi.FuenteProxy.service.impl;

import ar.utn.dssi.FuenteProxy.models.DTOs.output.HechoOutputDTO;
import ar.utn.dssi.FuenteProxy.models.entities.Hecho;
import ar.utn.dssi.FuenteProxy.models.entities.fuentes.Fuente;
import ar.utn.dssi.FuenteProxy.models.entities.fuentes.TipoFuente;
import ar.utn.dssi.FuenteProxy.models.entities.fuentes.adpaters.IServicioExternoAdapter;
import ar.utn.dssi.FuenteProxy.models.repositories.IFuenteRepository;
import ar.utn.dssi.FuenteProxy.models.repositories.IHechoRepository;
import ar.utn.dssi.FuenteProxy.service.IHechosService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static reactor.core.publisher.Flux.*;

@Service
public class HechosService implements IHechosService {

  private IHechoRepository hechoRepository;
  private IFuenteRepository fuenteRepository;


  public HechosService(IHechoRepository hechoRepository, IFuenteRepository fuenteRepository) {
    this.hechoRepository = hechoRepository;
    this.fuenteRepository = fuenteRepository;
  }

  @Override
  public List<HechoOutputDTO> obtenerHechos() {
    return hechoRepository.findAll();
  }

  @Override
  public List<HechoOutputDTO> obtenerHechosInstanciasMetamapa() {
    List<Fuente> fuentes = fuenteRepository.findTipo(TipoFuente.METAMAPA);
    //TODO: Ver si conviene hacerlo Mono o no.
    return List.of();
  }


  public void importarHechos() {
    List<Fuente> fuentes = fuenteRepository.findTipoNot(TipoFuente.METAMAPA);
    List<HechoOutputDTO> hechosImportados = fuentes.stream()
            .flatMap(fuente -> fuente.importarHechos().block().stream())
            .map(this::hechoOutputDTO)
            .toList();

    hechoRepository.saveAll(hechosImportados);
  }

  private HechoOutputDTO hechoOutputDTO(Hecho hecho) {
    var outputDTO = new HechoOutputDTO();

    outputDTO.setTitulo(hecho.getTitulo());
    outputDTO.setDescripcion(hecho.getDescripcion());
    outputDTO.setCategoria(hecho.getCategoria());
    outputDTO.setUbicacion(hecho.getUbicacion());
    outputDTO.setFechaAcontecimiento(hecho.getFechaAcontecimiento());
    outputDTO.setFechaCarga(hecho.getFechaCarga());

    return outputDTO;
  }
}


