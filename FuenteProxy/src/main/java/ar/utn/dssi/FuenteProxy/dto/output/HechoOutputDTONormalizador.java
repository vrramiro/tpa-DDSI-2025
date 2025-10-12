package ar.utn.dssi.FuenteProxy.dto.output;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class HechoOutputDTONormalizador {
  private String titulo;
  private String descripcion;
  private String categoria;
  private Double latitud;
  private Double longitud;
  private String fechaAcontecimiento; //TODO hacer cambio a LocalDateTime => tambien en el normalzador (inputDTO)
}
