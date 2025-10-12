package ar.utn.dssi.Agregador.models.DTOs.external;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.HechoInputDTO;
import lombok.Data;
import java.util.List;

@Data
public class HechosMetaMapa {
  private List<HechoInputDTO> hechos;
}
