package ar.utn.dssi.Agregador.models.entities.criteriosDePertenencia.impl;

import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.criteriosDePertenencia.CriterioDePertenencia;
import ar.utn.dssi.Agregador.models.entities.criteriosDePertenencia.TipoCriterio;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("por_provincia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CriterioPorProvincia extends CriterioDePertenencia {
  @Column(name = "provincia")
  private String provincia;

  @Override
  public Boolean loCumple(Hecho unHecho) {
    return unHecho.getUbicacion().getProvincia().equals(provincia);
  }

  @Override
  public TipoCriterio getTipoCriterio() {
    return TipoCriterio.PROVINCIA;
  }

  @Override
  public Boolean mismoValor(String valor) {
    return this.provincia.equals(valor);
  }
}
