package ar.utn.dssi.Agregador.models.entities.solicitud;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "solicitudes_de_eliminacion")
@Setter
@Getter
public class SolicitudDeEliminacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSolicitud;

    @Column(nullable = false, name = "id_hecho")
    private Long IdHecho;

    @Column(nullable = false, name = "descripcion")
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoDeSolicitud estadoDeSolicitud;

    @Column(nullable = false, name = "fecha_de_creacion")
    private LocalDateTime fechaDeCreacion;

    @Column(nullable = false, name = "fecha_de_evaluacion")
    private LocalDateTime fechaDeEvaluacion;

    @Column(nullable = false, name = "es_spam")
    private boolean esSpam;
}

