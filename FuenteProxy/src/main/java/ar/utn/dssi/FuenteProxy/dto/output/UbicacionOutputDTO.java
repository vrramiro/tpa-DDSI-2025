package ar.utn.dssi.FuenteProxy.dto.output;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UbicacionOutputDTO {
  private Double latitud;
  private Double longitud;

}
