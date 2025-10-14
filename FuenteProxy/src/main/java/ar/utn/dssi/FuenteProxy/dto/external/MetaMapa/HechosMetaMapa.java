package ar.utn.dssi.FuenteProxy.dto.external.MetaMapa;

import ar.utn.dssi.FuenteProxy.dto.output.HechoOutputDTO;
import lombok.Getter;
import java.util.List;

//La instancia metamapa no deja de enviar hechos que son de tipo output, sucede que vienen en una lista
@Getter
public class HechosMetaMapa {
  private List<HechoOutputDTO> hechosInstanciaMetaMapa;
}
