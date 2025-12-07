package ar.utn.dssi.Agregador.models.entities.solicitud;

import ar.utn.dssi.Agregador.models.entities.Hecho;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "solicitudes_de_edicion")
@Data
public class SolicitudDeEdicion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hecho_id", nullable = false)
    private Hecho hechoOriginal;

    private String nuevoTitulo;

    @Column(columnDefinition = "TEXT")
    private String nuevaDescripcion;

    private Long nuevoIdCategoria;

    @Enumerated(EnumType.STRING)
    private EstadoDeSolicitud estado;

    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaEvaluacion;

    @Column(name = "autor")
    private String autor;

    @Column(name = "gestionado_por")
    private String gestionadoPor;
}