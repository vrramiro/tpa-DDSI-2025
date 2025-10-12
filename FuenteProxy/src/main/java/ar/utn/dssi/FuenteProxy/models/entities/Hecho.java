package ar.utn.dssi.FuenteProxy.models.entities;

import ar.utn.dssi.FuenteProxy.models.entities.fuentes.Fuente;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "hecho")
public class Hecho {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "hecho_id_externo", nullable = false)
  private Integer idExterno; //para saber si ese hecho ya esta cargado

  @Column(name = "titulo", nullable = false)
  private String titulo;

  @Column(name = "descripcion", nullable = false, columnDefinition = "TEXT")
  private String descripcion;

  @Column(name = "titulo_sanitizado", nullable = false)
  private String tituloSanitizado;

  @Column(name = "descripcion_sanitizada", nullable = false, columnDefinition = "TEXT")
  private String descripcionSanitizada;

  @Embedded
  private Categoria categoria;

  @Embedded
  private Ubicacion ubicacion;

  @Column(name = "fecha_acontecimiento", nullable = false)
  private LocalDateTime fechaAcontecimiento;

  @Column(name = "fecha_carga", nullable = false)
  private LocalDateTime fechaCarga;

  @ManyToOne
  @JoinColumn(name = "fuente_id", nullable = false)
  private Fuente fuente;

  @Column(name = "eliminado", nullable = false)
  private Boolean eliminado;

  public String combinacionIdExternoFuenteId() {
    return this.idExterno + "-" + this.fuente.getId();
  }
}
