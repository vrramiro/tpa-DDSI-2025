package ar.utn.dssi.Agregador.models.entities.solicitud;

import ar.utn.dssi.Agregador.models.entities.Hecho;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "solicitudes_de_eliminacion")
@Getter
@Setter
public class SolicitudDeEliminacion {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idSolicitud;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "hecho_id", referencedColumnName = "id", nullable = false)
  private Hecho hecho;

  @Column(nullable = false, name = "descripcion", columnDefinition = "TEXT")
  private String descripcion;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private EstadoDeSolicitud estadoDeSolicitud;

  @Column(name = "fecha_de_creacion")
  private LocalDateTime fechaDeCreacion;

  @Column(name = "fecha_de_evaluacion")
  private LocalDateTime fechaDeEvaluacion;

  @Column(nullable = false, name = "es_spam")
  private boolean esSpam;

  @Column(name = "autor")
  private String autor;

  @Column(name = "gestionado_por")
  private String gestionadoPor;

  public void aceptar() {
    this.estadoDeSolicitud = EstadoDeSolicitud.ACEPTADA;
    this.fechaDeEvaluacion = LocalDateTime.now();
  }

  public void rechazar() {
    this.estadoDeSolicitud = EstadoDeSolicitud.RECHAZADA;
    this.fechaDeEvaluacion = LocalDateTime.now();
  }
}