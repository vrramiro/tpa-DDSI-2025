package ar.utn.dssi.app_web.dto.output;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SolicitudEdicionDTO {
    private Long id;
    private HechoOutputDTO hechoOriginal;
    private String nuevoTitulo;
    private String nuevaDescripcion;
    private Long nuevoIdCategoria;
    private String estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaEvaluacion;
}