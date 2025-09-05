package ar.utn.dssi.FuenteProxy.service.impl;

import ar.utn.dssi.FuenteProxy.models.DTOs.output.HechoOutputDTO;
import ar.utn.dssi.FuenteProxy.models.Errores.RepositorioVacio;
import ar.utn.dssi.FuenteProxy.models.adpaters.IServicioExternoAdapter;
import ar.utn.dssi.FuenteProxy.models.entities.Hecho;
import ar.utn.dssi.FuenteProxy.models.repositories.IHechosRepository;
import ar.utn.dssi.FuenteProxy.service.IHechosService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

import static reactor.core.publisher.Flux.*;

@Service
public class HechosService implements IHechosService {
  private List<IServicioExternoAdapter> serviciosExternos;
  //public IFuenteMetaMapa fuenteMetamapa; //una instancia de metamapa

  private IHechosRepository hechoRepository;

  public HechosService(List<IServicioExternoAdapter> serviciosExternos, IHechosRepository hechoRepository) {
    this.serviciosExternos = serviciosExternos;
    this.hechoRepository = hechoRepository;
  }

  @Override
  public List<HechoOutputDTO> obtenerHechos() {
    return List.of();
  }

  @Override
  public List<HechoOutputDTO> obtenerHechosInstanciasMetamapa() {
    return List.of();
  }

  public void importarHechos() {
    fromIterable(serviciosExternos)
            .flatMap(IServicioExternoAdapter::obtenerHechos)
            .flatMap(Flux::fromIterable)
            .doOnNext(hecho -> hechoRepository.save(hecho))
            .subscribe();
  }
/*
  @Override
  public List<HechoOutputDTO> obtenerHechos() {
    List<HechoOutputDTO> hechos = desastreNaturalesAdapter.obtenerHechos();

    if(hechos.isEmpty()) {
      throw new RepositorioVacio("No hay hechos en el repositorio proxy.");
    }

    return hechos;
  }

  @Override
  public List<HechoOutputDTO> obtenerHechosInstanciasMetamapa() {
    List<HechoOutputDTO> hechos = fuenteMetamapa.obtenerHechosMetamapa();

    if(hechos.isEmpty()) {
      throw new RepositorioVacio("No hay hechos en el repositorio proxy metamapa.");
    }

    return hechos;
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

*/
}
