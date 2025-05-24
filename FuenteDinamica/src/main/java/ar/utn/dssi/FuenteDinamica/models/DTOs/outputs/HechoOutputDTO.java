package ar.utn.dssi.FuenteDinamica.models.DTOs.outputs;

import ar.utn.dssi.FuenteDinamica.models.entities.Categoria;
import ar.utn.dssi.FuenteDinamica.models.entities.Ubicacion;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class HechoOutputDTO {
  private Long idHecho;
  private String titulo;
  private String descripcion;
  private Categoria categoria;
  private Ubicacion ubicacion;  //TODO: consultar si podemos enviar datos de tipo
  private LocalDateTime fechaAcontecimiento;
  private LocalDateTime fechaCarga;
}