package ar.utn.dssi.Agregador.models.entities;

import ar.utn.dssi.Agregador.models.entities.algoritmoConsenso.TipoConsenso;
import ar.utn.dssi.Agregador.models.entities.fuente.Fuente;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import java.util.ArrayList;
import java.util.List;

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

  @Column(nullable = false, name = "descripcion", columnDefinition = "TEXT")
  private String descripcion;

  @Column(nullable = false, name = "descripcion_sanitizada", columnDefinition = "TEXT")
  private String descripcionSanitizado;

  @Embedded
  private Categoria categoria;

  @Column(nullable = false, name = "clave_comparacion")
  private String claveComparacion;

  @Enumerated(EnumType.STRING)
  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "hecho_consenso", joinColumns = @JoinColumn(name = "hecho_id", referencedColumnName = "id"))
  @Column(name = "tipo_consenso")
  private List<TipoConsenso> consensosDados;

  @Embedded
  private Ubicacion ubicacion;

  @Getter
  @Column(nullable = false, name = "fechaAcontecimiento")
  private LocalDateTime fechaAcontecimiento;

  @Column(nullable = false, name = "fechaCarga")
  private LocalDateTime fechaCarga;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "hecho_id", referencedColumnName = "id")
  private List<ContenidoMultimedia> contenidoMultimedia;

  @Column(nullable = false, name = "visible")
  private Boolean visible;

  @Column(name = "autor")
  private String autor;

  public void agregarConsenso(TipoConsenso tipoConsenso) {
    if (this.consensosDados != null && !this.consensosDados.contains(tipoConsenso)) {
      this.consensosDados.add(tipoConsenso);
    }
  }

  public void resetearConsensos() {
    this.consensosDados = new ArrayList<>();
  }

}