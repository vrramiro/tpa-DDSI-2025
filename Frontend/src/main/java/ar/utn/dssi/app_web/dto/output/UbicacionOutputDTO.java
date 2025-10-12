package ar.utn.dssi.app_web.dto.output;

import lombok.Data;

@Data
public class UbicacionOutputDTO {
  private Double latitud;
  private Double longitud;
  private String pais;
  private String ciudad;
  private String provincia;
}
