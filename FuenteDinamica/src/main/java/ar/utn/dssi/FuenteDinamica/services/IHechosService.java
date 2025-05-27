package ar.utn.dssi.FuenteDinamica.services;

import ar.utn.dssi.FuenteDinamica.models.DTOs.inputs.HechoInputDTO;
import ar.utn.dssi.FuenteDinamica.models.DTOs.outputs.HechoOutputDTO;
import ar.utn.dssi.FuenteDinamica.models.entities.Hecho;

import java.util.List;

public interface IHechosService {
  List<HechoOutputDTO> obtenerHechos();
  HechoOutputDTO obtenerHechoPorId(Long idHecho);
  HechoOutputDTO crear(HechoInputDTO hecho);
  HechoOutputDTO hechoOutputDTO(Hecho hecho);

}
