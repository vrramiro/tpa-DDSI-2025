package ar.utn.dssi.FuenteProxy.models.fuenteMetamapa;

import ar.utn.dssi.FuenteProxy.models.DTOs.output.HechoOutputDTO;
import java.util.List;

public interface IFuenteMetaMapa {
  List<HechoOutputDTO> obtenerHechosMetamapa();
  //TODO: implementar traida de colecciones, deberia ser aca?
  //TODO: implementar traida de hechos filtrados, que pasa si el criterio de filtrado no existe?
}
