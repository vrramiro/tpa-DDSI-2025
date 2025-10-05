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

import java.time.LocalDate;

@Entity
@DiscriminatorValue("por_fecha_desde")
@Getter
@Setter
@NoArgsConstructor
@Table(name = "fecha_desde")
public class CriterioFechaDesde extends CriterioDePertenencia {
  @Column(name = "fecha_desde", nullable = false)
  private LocalDate fechaDesde;

  @Override
  public Boolean loCumple(Hecho unHecho) {
    return unHecho.getFechaCarga().toLocalDate().isAfter(this.fechaDesde);
  }
}