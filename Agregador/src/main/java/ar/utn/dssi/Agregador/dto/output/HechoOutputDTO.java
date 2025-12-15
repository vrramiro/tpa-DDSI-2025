package ar.utn.dssi.Agregador.dto.output;

import ar.utn.dssi.Agregador.models.entities.Hecho;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class HechoOutputDTO {
  private Long id;
  private String titulo;
  private String descripcion;
  private String categoria;
  private UbicacionOutputDTO ubicacion;
  @Setter
  private LocalDateTime fechaAcontecimiento;
  private LocalDateTime fechaCarga;
  private List<ContenidoMultimediaOuputDTO> contenidoMultimedia;

  private String autor;
  private Boolean visible;

  public static HechoOutputDTO fromEntity(Hecho hecho) {
    HechoOutputDTO dto = new HechoOutputDTO();
    dto.setId(hecho.getId());
    dto.setDescripcion(hecho.getDescripcion());
    dto.setFechaAcontecimiento(hecho.getFechaAcontecimiento());
    return dto;
  }

}