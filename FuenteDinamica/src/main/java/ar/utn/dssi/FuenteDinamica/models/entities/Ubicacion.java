package ar.utn.dssi.FuenteDinamica.models.entities;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class Ubicacion {

  private Double latitud;
  private Double longitud;
  private String pais;
  private String ciudad;
  private String provincia;
}