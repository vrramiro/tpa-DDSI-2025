package ar.utn.dssi.Agregador.models.entities.criteriosDePertenencia.impl;

import ar.utn.dssi.Agregador.models.entities.Categoria;
import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.criteriosDePertenencia.CriterioDePertenencia;
import ar.utn.dssi.Agregador.models.entities.criteriosDePertenencia.TipoCriterio;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;


@Entity
@DiscriminatorValue("por_categoria")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CriterioPorCategoria extends CriterioDePertenencia {
    @Column(name = "categoria")
    private String categoria;

    @Override
    public Boolean loCumple(Hecho hecho) {
        return this.categoria.equals(hecho.getCategoria().getNombre());
    }

    @Override
    public TipoCriterio getTipoCriterio() {
        return TipoCriterio.CATEGORIA;
    }

    @Override
    public Boolean mismoValor(String valor) {
      return this.categoria.equals(valor);
    }


}
