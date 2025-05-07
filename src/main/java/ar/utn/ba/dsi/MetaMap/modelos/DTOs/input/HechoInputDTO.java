package ar.utn.ba.dsi.MetaMap.modelos.DTOs.input;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class HechoInputDTO {
    private String titulo;
    private String descripcion;
    private Long idCategoria;
    private Long idUbicacion;
    private LocalDateTime fechaAcontecimiento;
    private List<Long> idsEtiquetas;
}
