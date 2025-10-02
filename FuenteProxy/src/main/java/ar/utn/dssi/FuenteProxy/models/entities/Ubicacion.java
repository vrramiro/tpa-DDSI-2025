package ar.utn.dssi.FuenteProxy.models.entities;

import jakarta.persistence.Column;
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
  @Column(name = "latitud")
  private Double latitud;

  @Column(name = "longitud")
  private Double longitud;

  @Column(name = "pais")
  private String pais;

  @Column(name = "ciudad")
  private String ciudad;

  @Column(name = "provincia")
  private String provincia;

  public Boolean invalida() {
    return this.ciudad == null || this.provincia == null;
  }
}
