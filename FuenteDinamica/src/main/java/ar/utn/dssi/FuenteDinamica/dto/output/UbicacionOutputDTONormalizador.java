package ar.utn.dssi.FuenteDinamica.dto.output;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UbicacionOutputDTONormalizador {
  private Double latitud;
  private Double longitud;
}