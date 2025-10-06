package ar.utn.dssi.Agregador.services;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.CriterioDePertenenciaInputDTO;
import ar.utn.dssi.Agregador.models.entities.Coleccion;
import java.util.List;

public interface ICriterioDePertenenciaService {
  Boolean actualizarCriterios(Coleccion coleccion, List<CriterioDePertenenciaInputDTO> criteriosNuevos);
}
