package ar.utn.dssi.Agregador.services;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.HechoInputDTO;
import ar.utn.dssi.Agregador.models.entities.content.Hecho;
import java.util.List;

public interface IFuentesService {
  List<HechoInputDTO> obtenerNuevosHechos();
}
