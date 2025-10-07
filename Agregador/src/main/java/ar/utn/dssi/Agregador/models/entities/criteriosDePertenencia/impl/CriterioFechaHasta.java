package ar.utn.dssi.Agregador.models.entities.criteriosDePertenencia.impl;

import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.criteriosDePertenencia.CriterioDePertenencia;
import ar.utn.dssi.Agregador.models.entities.criteriosDePertenencia.TipoCriterio;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("por_fecha_hasta")
@Getter
@Setter
@NoArgsConstructor
public class CriterioFechaHasta extends CriterioDePertenencia {
  @Column(name = "fecha_hasta", nullable = false)
  private LocalDate fechaHasta;

  @Override
  public Boolean loCumple(Hecho unHecho) {
    return unHecho.getFechaCarga().toLocalDate().isBefore(this.fechaHasta);
  }

  @Override
  public TipoCriterio getTipoCriterio() {
    return TipoCriterio.FECHA_HASTA;
  }

  @Override
  public Boolean mismoValor(String valor) {
    return this.fechaHasta.toString().equals(valor);
  }
}
