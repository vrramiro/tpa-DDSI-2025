package ar.utn.dssi.FuenteDinamica.models.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ubicacion {
  private Double latitud;
  private Double longitud;
  private String pais;
  private String ciudad;
  private String provincia;
}