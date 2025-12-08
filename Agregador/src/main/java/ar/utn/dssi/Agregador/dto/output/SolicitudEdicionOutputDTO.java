package ar.utn.dssi.Agregador.dto.output;

import ar.utn.dssi.Agregador.models.entities.solicitud.EstadoDeSolicitud;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SolicitudEdicionOutputDTO {
    private Long id;
    private HechoOutputDTO hechoOriginal;
    private String nuevoTitulo;
    private String nuevaDescripcion;
    private Long nuevoIdCategoria;
    private EstadoDeSolicitud estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaEvaluacion;
}