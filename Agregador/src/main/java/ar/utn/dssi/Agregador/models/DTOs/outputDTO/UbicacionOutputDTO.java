package ar.utn.dssi.Agregador.models.DTOs.outputDTO;

import lombok.Data;

@Data
public class UbicacionOutputDTO {
  private String pais;
  private String provincia;
  private String ciudad;
  private Double latitud;
  private Double longitud;
}
