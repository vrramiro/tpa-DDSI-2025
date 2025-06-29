package ar.utn.dssi.Agregador.models.DTOs.external;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.HechoInputDTO;
import lombok.Data;
import java.util.List;

//TODO revisar si esta bien o como deberia ser el tipo dto externo para los hechos que vienen de las fuentes => ahora mismo implica que todas devuelven los hechos de la misma forma
//ME PARECE QUE ESTA BIEN, LAS FUENTES DEBEN DARNOS HECHOS CON EL FORMATO DE INPUT QUE NECESITAMOS, ESTAMOS HACIENDO QUE COINCIDN EN EL ENVIO DE LOS DTOS
@Data
public class HechosDeFuente {
  private List<HechoInputDTO> hechos;
}
