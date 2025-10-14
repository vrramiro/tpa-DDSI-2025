package ar.utn.dssi.app_web.dto.input;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Data
public class HechoRequest {
  private String titulo;
  private String descripcion;
  private Long idCategoria;
  private Double latitud;
  private Double longitud;
  private LocalDate fechaAcontecimiento;
  private List<MultipartFile> contenidoMultimedia;
}