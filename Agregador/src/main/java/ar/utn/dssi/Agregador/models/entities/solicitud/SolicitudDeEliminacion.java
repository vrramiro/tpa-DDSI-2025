package ar.utn.dssi.Agregador.models.entities.solicitud;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class SolicitudDeEliminacion {
    private Long IdHecho;
    private String descripcion;
    private EstadoDeSolicitud estadoDeSolicitud;
    private LocalDateTime fechaDeCreacion;
    private LocalDateTime fechaDeEvaluacion;
    private boolean esSpam;
    private Long idSolicitud;
}

