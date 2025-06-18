package ar.utn.dssi.Agregador.services;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.HechoInputDTO;
import ar.utn.dssi.Agregador.models.entities.fuente.Fuente;
import lombok.Getter;
import java.util.List;

public interface IFuentesService {
  List<HechoInputDTO> obtenerNuevosHechos();
  List<HechoInputDTO> obtenerHechosProxy();
  void agregarFuente(Fuente fuente);
}
