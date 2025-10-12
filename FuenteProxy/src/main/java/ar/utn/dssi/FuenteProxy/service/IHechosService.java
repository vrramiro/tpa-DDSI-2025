package ar.utn.dssi.FuenteProxy.service;

import ar.utn.dssi.FuenteProxy.models.DTOs.output.HechoOutputDTO;
import java.util.List;

public interface IHechosService {
  List<HechoOutputDTO> obtenerHechos();
  List<HechoOutputDTO> obtenerHechosInstanciasMetamapa();
  void importarHechos();
}
