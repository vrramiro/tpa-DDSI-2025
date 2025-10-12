package ar.utn.dssi.Agregador.dto.input;

import lombok.Data;

@Data
public class UbicacionInputDTO {
  private Double latitud;
  private Double longitud;
  private String pais;
  private String ciudad;
  private String provincia;
}
