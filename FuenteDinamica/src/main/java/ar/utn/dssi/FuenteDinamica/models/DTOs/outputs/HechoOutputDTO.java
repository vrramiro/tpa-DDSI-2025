package ar.utn.dssi.FuenteDinamica.models.DTOs.outputs;

import ar.utn.dssi.FuenteDinamica.models.entities.ContenidoMultimedia;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Setter
public class HechoOutputDTO {
  private Long idHechoOrigen;
  private String titulo;
  private String descripcion;
  private String tituloSanitizado;
  private String descripcionSanitizado;
  private CategoriaOutputDTO categoria;
  private UbicacionOutputDTO ubicacion;
  private LocalDateTime fechaAcontecimiento;
  private LocalDateTime fechaCarga;
  private List<String> contenidoMultimedia;
}