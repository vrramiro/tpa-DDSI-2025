package ar.utn.dssi.Agregador.models.entities;

import java.util.List;
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
@Table(name = "coleccion")
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
        this.hechos.addAll(nuevosHechos.stream().filter(this::lePertenece).toList());
    }

    private Boolean lePertenece(Hecho hecho) {
        return this.criterios.stream()
            .allMatch(criterio -> criterio.loCumple(hecho))
            && this.fuentes.contains(hecho.getFuente());
    }

    public Boolean tieneFuente(Fuente fuente) {
        return this.fuentes.contains(fuente);
    }

    public void actualizarCriterios(List<CriterioDePertenencia> criterios) {
        this.criterios.clear();
        this.criterios.addAll(criterios);
    }

    public void liberarHechos() {
        this.hechos.clear();
    }

    public void actualizarFuentes(List<Fuente> fuentes) {
        this.fuentes.clear();
        this.fuentes.addAll(fuentes);
    }
}