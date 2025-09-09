package ar.utn.dssi.Agregador.models.entities;

import ar.utn.dssi.Agregador.models.entities.fuente.Fuente;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Hecho {
    private Long id;
    private Long idEnFuente;
    private Fuente fuente;
    private String titulo;
    private String descripcion;
    private Categoria categoria;
    private Ubicacion ubicacion;
    private LocalDateTime fechaAcontecimiento;
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