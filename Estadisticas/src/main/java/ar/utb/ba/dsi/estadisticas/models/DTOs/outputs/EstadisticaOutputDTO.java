package ar.utb.ba.dsi.estadisticas.models.DTOs.outputs;

import ar.utb.ba.dsi.estadisticas.models.entities.TipoEstadistica;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EstadisticaOutputDTO {
    private TipoEstadistica tipo;
    private String nombreColeccion;
    private String nombreCategoria;
    private Long valor;
    private String clave;
    private LocalDateTime fechaDeCalculo;
}
