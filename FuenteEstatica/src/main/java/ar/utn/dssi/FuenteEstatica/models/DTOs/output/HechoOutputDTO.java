package ar.utn.dssi.FuenteEstatica.models.DTOs.output;


import ar.utn.dssi.FuenteEstatica.models.entities.contenido.Categoria;
import ar.utn.dssi.FuenteEstatica.models.entities.contenido.Ubicacion;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class HechoOutputDTO {
  private Long idHechoOrigen;
  private String titulo;
  private String descripcion;
  private Categoria categoria;
  private Ubicacion ubicacion;
  private LocalDateTime fechaAcontecimiento;
  private LocalDateTime fechaCarga;
}