package ar.utn.dssi.app_web.DTO.output;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class HechoOutputDTO {
    private String titulo;
    private String descripcion;
    private String categoria;
    private UbicacionOutputDTO ubicacion;
    private LocalDateTime fechaAcontecimiento;
    private LocalDateTime fechaCarga;
    private List<ContenidoMultimediaOuputDTO> contenidoMultimedia;
}

