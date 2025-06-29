package ar.utn.dssi.FuenteDinamica.services;

import ar.utn.dssi.FuenteDinamica.models.DTOs.inputs.HechoInputDTO;
import ar.utn.dssi.FuenteDinamica.models.DTOs.outputs.HechoOutputDTO;
import ar.utn.dssi.FuenteDinamica.models.entities.Hecho;

import java.util.List;

public interface IHechosService {
  List<HechoOutputDTO> obtenerHechos();
  List<HechoOutputDTO> obtenerHechosNuevos();
  HechoOutputDTO obtenerHechoPorId(Long idHecho);
  HechoOutputDTO crear(HechoInputDTO hecho);
  HechoOutputDTO hechoOutputDTO(Hecho hecho);
  void editarHecho(HechoInputDTO hecho, Long idHecho);
  Boolean hechoEditable(Long idHecho);
  void eliminarHecho(Long idHecho);
  List<HechoOutputDTO> obtenerHechosEditados();
}
