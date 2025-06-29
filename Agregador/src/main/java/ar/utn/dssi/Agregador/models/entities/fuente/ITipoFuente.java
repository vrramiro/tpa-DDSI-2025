package ar.utn.dssi.Agregador.models.entities.fuente;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.HechoInputDTO;
import ar.utn.dssi.Agregador.models.entities.Origen;
import java.util.List;

public interface ITipoFuente {
  public List<HechoInputDTO> obtenerHechos();
  public Origen getTipo();
}
