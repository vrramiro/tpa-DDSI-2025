package ar.utn.dssi.Agregador.dto.output;

import lombok.Data;

@Data
public class UbicacionOutputDTO {
  private String pais;
  private String provincia;
  private String ciudad;
  private Double latitud;
  private Double longitud;
}
