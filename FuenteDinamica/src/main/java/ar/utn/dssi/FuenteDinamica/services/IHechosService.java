package ar.utn.dssi.FuenteDinamica.services;

import ar.utn.dssi.FuenteDinamica.models.DTOs.inputs.HechoInputDTO;
import ar.utn.dssi.FuenteDinamica.models.DTOs.outputs.HechoOutputDTO;
import ar.utn.dssi.FuenteDinamica.models.entities.Hecho;

import java.util.List;

public interface IHechosService {
  public List<HechoOutputDTO> obtenerHechos();
  public HechoOutputDTO obtenerHechoPorId(Long idHecho);
  public HechoOutputDTO crear(HechoInputDTO hecho);
  public HechoOutputDTO hechoOutputDTO(Hecho hecho);
}
