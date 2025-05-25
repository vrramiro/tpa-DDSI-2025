package ar.utn.dssi.FuenteProxy.models.adapter;

import ar.utn.dssi.FuenteProxy.models.DTOs.output.HechoOutputDTO;
import java.util.List;

public interface IServicioExternoAdapter {
  public List<HechoOutputDTO> obtenerHechos();
}
