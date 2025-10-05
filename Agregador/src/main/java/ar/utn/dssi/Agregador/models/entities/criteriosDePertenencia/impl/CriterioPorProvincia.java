package ar.utn.dssi.Agregador.models.entities.criteriosDePertenencia.impl;

import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.criteriosDePertenencia.CriterioDePertenencia;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("por_provincia")
@Getter
@Setter
@NoArgsConstructor
@Table(name = "por_provincia")
public class CriterioPorProvincia extends CriterioDePertenencia {
  @Column(name = "provincia", nullable = false)
  private String provincia;

  @Override
  public Boolean loCumple(Hecho unHecho) {
    return unHecho.getUbicacion().getProvincia().equals(provincia);
  }
}
