package ar.utn.dssi.FuenteDinamica.models.DTOs.outputs;

import ar.utn.dssi.FuenteDinamica.models.entities.Categoria;
import ar.utn.dssi.FuenteDinamica.models.entities.Origen;
import ar.utn.dssi.FuenteDinamica.models.entities.Ubicacion;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class HechoOutputDTO {
  // private Long idHecho;
  private String titulo;
  private String descripcion;
  private Categoria categoria;
  private Ubicacion ubicacion;
  private LocalDateTime fechaAcontecimiento;
  private LocalDateTime fechaCarga;
  private Origen origen;
  private List<MultipartFile> contenidoMultimedia;
}