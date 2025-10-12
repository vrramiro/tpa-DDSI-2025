package ar.utn.dssi.FuenteProxy.service;

import ar.utn.dssi.FuenteProxy.dto.output.HechoOutputDTO;
import java.time.LocalDateTime;
import java.util.List;

public interface IHechosService {
  List<HechoOutputDTO> obtenerHechos(LocalDateTime fechaUltimaComunicacion);

  List<HechoOutputDTO> obtenerHechosInstanciasMetamapa();

  void importarHechos();

  void eliminarHecho(Long idHecho);
}
