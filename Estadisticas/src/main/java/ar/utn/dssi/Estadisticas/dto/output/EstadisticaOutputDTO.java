package ar.utn.dssi.Estadisticas.dto.output;

import ar.utn.dssi.Estadisticas.models.entities.TipoEstadistica;
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
