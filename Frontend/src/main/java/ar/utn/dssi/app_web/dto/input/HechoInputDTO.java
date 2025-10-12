package ar.utn.dssi.app_web.dto.input;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class HechoInputDTO {
  private String titulo;
  private String descripcion;
  private CategoriaInputDTO categoria;
  private Double latitud;
  private Double longitud;
  private LocalDateTime fechaAcontecimiento;
  private List<MultipartFile> contenidoMultimedia;
}
