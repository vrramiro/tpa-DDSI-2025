package ar.utn.dssi.Agregador.models.entities;

import ar.utn.dssi.Agregador.models.entities.fuente.Fuente;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

  @Column(nullable = false, name = "titulo_sanitizado")
  private String tituloSanitizado;

  @Column(nullable = false, name = "descripcion")
  private String descripcion;

  @Column(nullable = false, name = "descripcion_sanitizada")
  private String descripcionSanitizado;

  @Embedded
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


  public Boolean mismoHecho(Hecho otroHecho) {
    return this.titulo.equals(otroHecho.getTitulo()) &&
        this.categoria.equals(otroHecho.getCategoria()) &&
        this.ubicacion.equals(otroHecho.getUbicacion()) &&
        this.fechaAcontecimiento.equals(otroHecho.getFechaAcontecimiento());
  }


  public boolean tituloSimilar(Hecho otroHecho) {
    return this.getTitulo().equals(otroHecho.getTituloSanitizado());
  }

  public boolean mismosAtributos(Hecho otroHecho) {
    return this.descripcion.equals(otroHecho.getDescripcion())
        || this.categoria.equals(otroHecho.getCategoria())
        || this.ubicacion.equals(otroHecho.getUbicacion())
        || this.fechaAcontecimiento.equals(otroHecho.getFechaAcontecimiento());
  }

  public boolean distintaFuente(Hecho hecho) {
    return (!Objects.equals(hecho.fuente.getId(), this.fuente.getId()));
  }
}