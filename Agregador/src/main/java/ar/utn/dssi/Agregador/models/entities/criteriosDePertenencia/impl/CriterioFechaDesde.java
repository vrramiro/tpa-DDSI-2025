package ar.utn.dssi.Agregador.models.entities.criteriosDePertenencia.impl;

import ar.utn.dssi.Agregador.mappers.MapperDeCriterio;
import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.criteriosDePertenencia.CriterioDePertenencia;
import ar.utn.dssi.Agregador.models.entities.criteriosDePertenencia.TipoCriterio;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("por_fecha_desde")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CriterioFechaDesde extends CriterioDePertenencia {

  @Column(name = "fecha_desde")
  private LocalDate fechaDesde;

  @Override
  public Boolean loCumple(Hecho unHecho) {
    return unHecho.getFechaCarga().toLocalDate().isAfter(this.fechaDesde);
  }

  @Override
  public TipoCriterio getTipoCriterio() {
    return TipoCriterio.FECHA_DESDE;
  }

  @Override
  public Boolean mismoValor(String valor) {
    return this.fechaDesde.toString().equals(valor);
  }

  @Override
  public String getValor() {
    return this.fechaDesde.toString();
  }

  @Override
  public boolean setValor(String valor) {
    boolean seActualizo = false;

    if (!mismoValor(valor)) {
      this.fechaDesde = MapperDeCriterio.parsearFecha(valor);
      seActualizo = true;
    }

    return seActualizo;
  }
}