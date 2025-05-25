package ar.utn.dssi.FuenteProxy.service.impl;

import ar.utn.dssi.FuenteProxy.models.DTOs.output.HechoOutputDTO;
import ar.utn.dssi.FuenteProxy.models.adapter.IServicioExternoAdapter;
import ar.utn.dssi.FuenteProxy.models.adapter.adaptadoresConcretos.DesastresNaturalesAdapter;
import ar.utn.dssi.FuenteProxy.models.entities.Hecho;
import ar.utn.dssi.FuenteProxy.service.IHechosService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HechosService implements IHechosService {
  public IServicioExternoAdapter desastreNaturalesAdapter;

  public HechosService() {
    this.desastreNaturalesAdapter = new DesastresNaturalesAdapter();
  }

  public List<HechoOutputDTO> obtenerHechos() {
    return desastreNaturalesAdapter.obtenerHechos();
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
