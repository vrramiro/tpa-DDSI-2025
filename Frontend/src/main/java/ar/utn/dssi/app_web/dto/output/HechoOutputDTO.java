package ar.utn.dssi.app_web.dto.output;

import ar.utn.dssi.app_web.dto.EstadoHecho;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class HechoOutputDTO {
  private Long id;
  private String titulo;
  private String descripcion;
  private String categoria;
  private UbicacionOutputDTO ubicacion;
  private LocalDate fechaAcontecimiento;
  private LocalDate fechaCarga;
  private List<ContenidoMultimediaOuputDTO> contenidoMultimedia;
  private EstadoHecho estado;

  private String autor;
  private Boolean visible;

  private String tipoFuente;
}