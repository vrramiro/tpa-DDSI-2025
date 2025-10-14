package ar.utn.dssi.FuenteProxy.dto.input;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UbicacionInputDTONormalizador {
  private Double latitud;
  private Double longitud;
  private String pais;
  private String ciudad;
  private String provincia;
}