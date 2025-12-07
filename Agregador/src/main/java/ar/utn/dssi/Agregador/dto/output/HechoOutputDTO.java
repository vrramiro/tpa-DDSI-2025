package ar.utn.dssi.Agregador.dto.output;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class HechoOutputDTO {
  private Long id;
  private String titulo;
  private String descripcion;
  private String categoria;
  private UbicacionOutputDTO ubicacion;
  private LocalDateTime fechaAcontecimiento;
  private LocalDateTime fechaCarga;
  private List<ContenidoMultimediaOuputDTO> contenidoMultimedia;

  private String autor;
}