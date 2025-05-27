package src.main.java.ar.utn.dssi.Agregador.models.entities.solicitud;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class SolicitudDeEdicion {
    private Hecho hechoEditado;
    private Long idHecho;
    private EstadoDeSolicitudEdicion estadoDeSolicitud;
    private LocalDateTime fechaDeCreacion;
    private LocalDateTime fechaDeEvaluacion;
    private Long idSolicitud;
}
