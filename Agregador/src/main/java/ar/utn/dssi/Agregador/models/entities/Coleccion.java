package ar.utn.dssi.Agregador.models.entities;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import ar.utn.dssi.Agregador.models.entities.algoritmoConsenso.TipoConsenso;
import ar.utn.dssi.Agregador.models.entities.criteriosDePertenencia.CriterioDePertenencia;
import ar.utn.dssi.Agregador.models.entities.fuente.Fuente;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Setter;
import lombok.Getter;

@Entity
@Table(name = "colecciones")
@Setter
@Getter
public class Coleccion {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String handle;

    @Column(nullable = false, name = "titulo")
    private String titulo;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "coleccion_hecho",
        joinColumns = @JoinColumn(name = "coleccion_id", referencedColumnName = "handle"),
        inverseJoinColumns = @JoinColumn(name = "hecho_id", referencedColumnName = "id")
    )
    private List<Hecho> hechos;

    @Column(nullable = false, name = "descripcion")
    private String descripcion;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true) //orphanRemoval para que si se elimina el criterio de la coleccion, se elimine de la bd
    @JoinColumn(name = "coleccion_id", referencedColumnName = "handle")
    private List<CriterioDePertenencia> criterios;

    @Enumerated(EnumType.STRING)
    @Column(name = "consenso_aceptado")
    private TipoConsenso consenso;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "coleccion_fuente",
        joinColumns = @JoinColumn(name = "coleccion_id", referencedColumnName = "handle"),
        inverseJoinColumns = @JoinColumn(name = "fuente_id", referencedColumnName = "fuente_id")
    )
    private List<Fuente> fuentes;

    @Column(nullable = false, name = "actualizada")
    private Boolean actualizada;

    public void agregarHechos(List<Hecho> nuevosHechos) {
        Set<Long> idsFuentes = this.fuentes.stream()
            .map(Fuente::getId)
            .collect(Collectors.toSet());

        nuevosHechos.stream()
            .filter(h -> idsFuentes.contains(h.getFuente().getId()))
            .filter(h -> this.criterios.stream().allMatch(c -> c.loCumple(h)))
            .forEach(h -> this.hechos.add(h));
    }

    public Boolean tieneFuente(Fuente fuente) {
        return this.fuentes.stream()
            .anyMatch(f -> f.getId().equals(fuente.getId()));
    }

    public void liberarHechos() {
        this.hechos.clear();
    }

    public void marcarComoActualizada() {
        this.actualizada = Boolean.TRUE;
    }
}