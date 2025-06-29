package ar.utn.dssi.FuenteProxy.service.impl;

import ar.utn.dssi.FuenteProxy.models.DTOs.output.HechoOutputDTO;
import ar.utn.dssi.FuenteProxy.models.Errores.RepositorioVacio;
import ar.utn.dssi.FuenteProxy.models.adapters.IServicioExternoAdapter;
import ar.utn.dssi.FuenteProxy.models.adapters.adaptadoresConcretos.DesastresNaturalesAdapter;
import ar.utn.dssi.FuenteProxy.models.entities.Hecho;
import ar.utn.dssi.FuenteProxy.models.fuenteMetamapa.IFuenteMetaMapa;
import ar.utn.dssi.FuenteProxy.service.IHechosService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HechosService implements IHechosService {
  public IServicioExternoAdapter desastreNaturalesAdapter;
  public IFuenteMetaMapa fuenteMetamapa; //una instancia de metamapa

  //POR AHORA SE INYECTA DESPUES VA A CAMBIAR AL USAR VARIAS INSTANCIAS
  public HechosService(IFuenteMetaMapa fuenteMetamapa) {
    this.desastreNaturalesAdapter = new DesastresNaturalesAdapter();
  }

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
}
