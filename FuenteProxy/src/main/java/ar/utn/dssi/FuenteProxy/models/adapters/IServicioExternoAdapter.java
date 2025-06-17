package ar.utn.dssi.FuenteProxy.models.adapters;

import ar.utn.dssi.FuenteProxy.models.DTOs.output.HechoOutputDTO;
import java.util.List;

public interface IServicioExternoAdapter {
  public List<HechoOutputDTO> obtenerHechos();
}
