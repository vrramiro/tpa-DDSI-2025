package ar.utn.dssi.Agregador.dto.output;

import ar.utn.dssi.Agregador.models.entities.Categoria;
import ar.utn.dssi.Agregador.models.entities.Ubicacion;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import java.util.List;

@Setter
public class FuenteServiceOutputDTO {
  private Long idFuente;
  private Long idEnFuente; //Viene desde la fuente
  private String titulo;
  private String descripcion;
  private Categoria categoria;
  private Ubicacion ubicacion;
  private LocalDateTime fechaAcontecimiento;
  private LocalDateTime fechaCarga;
  private List<MultipartFile> contenidoMultimedia;
}
