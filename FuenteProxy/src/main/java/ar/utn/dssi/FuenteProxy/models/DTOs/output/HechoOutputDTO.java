package ar.utn.dssi.FuenteProxy.models.DTOs.output;

import ar.utn.dssi.FuenteProxy.models.entities.Categoria;
import ar.utn.dssi.FuenteProxy.models.entities.Ubicacion;
import java.time.LocalDateTime;

//TODO revisar
public class HechoOutputDTO {
  private String titulo;
  private String descripcion;
  private Categoria categoria;
  private Ubicacion ubicacion;
  private LocalDateTime fechaAcontecimiento;
  private LocalDateTime fechaCarga;
}
