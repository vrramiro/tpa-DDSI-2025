package ar.utn.dssi.Agregador.models.entities.fuente;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.HechoInputDTO;
import java.util.List;

public interface IFuenteRefrescable {
  List<HechoInputDTO> obtenerNuevosHechos();
}
