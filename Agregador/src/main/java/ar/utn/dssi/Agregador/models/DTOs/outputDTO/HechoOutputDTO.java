package ar.utn.dssi.Agregador.models.DTOs.outputDTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class HechoOutputDTO {
    private String titulo;
    private String descripcion;
    private CategoriaOutputDTO categoria;
    private UbicacionOutputDTO ubicacion;
    private LocalDateTime fechaAcontecimiento;
    private LocalDateTime fechaCarga;
    private List<ContenidoMultimediaOuputDTO> contenidoMultimedia;
}