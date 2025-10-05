package ar.utn.dssi.Agregador.models.entities.criteriosDePertenencia.impl;

import ar.utn.dssi.Agregador.models.entities.Categoria;
import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.criteriosDePertenencia.CriterioDePertenencia;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@Table(name = "por_categoria")
public class CriterioPorCategoria extends CriterioDePertenencia {
    @ManyToMany
    @JoinTable(
        name = "criterio_categoria",
        joinColumns = @JoinColumn(name = "criterio_id"),
        inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private List<Categoria> categorias;

    @Override
    public Boolean loCumple(Hecho hecho) {
        return this.categorias.contains(hecho.getCategoria());
    }

    public void agregarCategorias(Categoria categoria) {
        this.categorias.add(categoria);
    }
}
