package ar.utn.dssi.Agregador.models.entities.solicitud;

import ar.utn.dssi.Agregador.models.entities.content.Hecho;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class SolicitudDeEliminacion {
    private Hecho hecho;
    private String descripcion;
    private EstadoDeSolicitud estadoDeSolicitud;
    private LocalDateTime fechaDeCreacion;
    private LocalDateTime fechaDeEvaluacion;
    private boolean esSpam;
    private Long idSolicitud;

    @Getter
    private static Integer caracteresMinimos = 500;
}

