package ar.utn.dssi.FuenteDinamica.services;

import ar.utn.dssi.FuenteDinamica.models.DTOs.outputs.HechoOutputDTO;
import java.util.List;

public interface IHechosService {
  public List<HechoOutputDTO> obtenerHechos();
}
