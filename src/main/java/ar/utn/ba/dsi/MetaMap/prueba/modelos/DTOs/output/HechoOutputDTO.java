package ar.utn.ba.dsi.MetaMap.prueba.modelos.DTOs.output;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class HechoOutputDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private Long idCategoria;
    private Long idUbicacion;
    private LocalDateTime fechaAcontecimiento;
    private LocalDateTime fechaCarga;
    private String origen;
    private List<Long> idsEtiquetas;
}
