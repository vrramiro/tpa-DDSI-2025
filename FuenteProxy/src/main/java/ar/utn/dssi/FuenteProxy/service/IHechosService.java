package ar.utn.dssi.FuenteProxy.service;

import ar.utn.dssi.FuenteProxy.models.DTOs.output.HechoOutputDTO;
import ar.utn.dssi.FuenteProxy.models.entities.Hecho;
import org.springframework.stereotype.Service;
import java.util.List;

public interface IHechosService {
  public List<HechoOutputDTO> obtenerHechos();
}
