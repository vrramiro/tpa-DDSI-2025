package ar.utn.dssi.FuenteProxy.models.entities;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ubicacion {
  private Double latitud;
  private Double longitud;
  private String pais;
  private String ciudad;
  private String provincia;
}
