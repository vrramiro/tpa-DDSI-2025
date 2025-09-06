package ar.utn.dssi.FuenteDinamica.models.entities;

import jakarta.persistence.*;
import lombok.*;

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