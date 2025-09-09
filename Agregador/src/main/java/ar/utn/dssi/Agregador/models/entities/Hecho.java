package ar.utn.dssi.Agregador.models.entities;

import ar.utn.dssi.Agregador.models.entities.fuente.Fuente;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "hecho")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Hecho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "id_en_fuente")
    private Long idEnFuente;

    @ManyToOne
    @JoinColumn(name = "fuente_id", nullable = false)
    private Fuente fuente;

    @Column(nullable = false, name = "titulo")
    private String titulo;

    @Column(nullable = false, name = "descripcion")
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @Embedded
    private Ubicacion ubicacion;

    @Column(nullable = false, name = "fechaAcontecimiento")
    private LocalDateTime fechaAcontecimiento;

    @Column(nullable = false, name = "fechaCarga")
    private LocalDateTime fechaCarga;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "hecho_id", referencedColumnName = "id")
    private List<ContenidoMultimedia> contenidoMultimedia;

    @Column(nullable = false, name = "visible")
    private Boolean visible;

    //TODO: ver si hacen falta los datos ya sanitizados => titulo y descripcion directamente guardados sanitizados + los originales

    public Boolean mismoHecho(Hecho otroHecho) {
        return this.getTitulo().equals(otroHecho.getTitulo()) &&
            this.getCategoria().equals(otroHecho.getCategoria()) &&
            this.getUbicacion().equals(otroHecho.getUbicacion()) &&
            this.getFechaAcontecimiento().equals(otroHecho.getFechaAcontecimiento());
    }

    public boolean tituloSimilar(Hecho otroHecho) {
        return true; //TODO: Implementar, seguramente usando similitud de cosenos y TF-IDF (aptovechando alguna libreria)
    }

    public boolean mismosAtributos(Hecho otroHecho) {
        return this.getDescripcion().equals(otroHecho.getDescripcion())
            || this.getCategoria().equals(otroHecho.getCategoria())
            || this.getUbicacion().equals(otroHecho.getUbicacion())
            || this.getFechaAcontecimiento().equals(otroHecho.getFechaAcontecimiento());
    }

    public boolean distintaFuente(Hecho hecho) {
        return (!Objects.equals(hecho.fuente.getId(), this.fuente.getId()));
    }
}