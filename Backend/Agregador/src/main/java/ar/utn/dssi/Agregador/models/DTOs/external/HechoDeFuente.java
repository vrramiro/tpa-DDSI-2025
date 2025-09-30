package ar.utn.dssi.Agregador.models.DTOs.external;

import ar.utn.dssi.Agregador.models.entities.Categoria;
import ar.utn.dssi.Agregador.models.entities.Ubicacion;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class HechoDeFuente {
  private Long idEnFuente; //Viene desde la fuente
  private String titulo;
  private String descripcion;
  private Categoria categoria;
  private Ubicacion ubicacion;
  private LocalDateTime fechaAcontecimiento;
  private LocalDateTime fechaCarga;
  private List<MultipartFile> contenidoMultimedia;
}
