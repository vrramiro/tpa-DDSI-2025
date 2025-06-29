package ar.utn.dssi.Agregador.services;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.HechoInputDTO;
import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.fuente.Fuente;
import java.util.List;

public interface IFuentesService {
  List<Hecho> obtenerNuevosHechos();
  List<HechoInputDTO> obtenerHechosProxy();
  void agregarFuente(Fuente fuente);
  public void eliminarHecho(Long IdEnFuente, Long IdFuenteOrigen);

}
