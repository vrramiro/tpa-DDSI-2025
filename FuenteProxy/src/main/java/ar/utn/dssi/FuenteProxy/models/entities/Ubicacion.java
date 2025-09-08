package ar.utn.dssi.FuenteProxy.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Embeddable
public class Ubicacion {
  @Column(name = "latitud")
  private Long latitud;

  @Column(name = "longitud")
  private Long longitud;
}
