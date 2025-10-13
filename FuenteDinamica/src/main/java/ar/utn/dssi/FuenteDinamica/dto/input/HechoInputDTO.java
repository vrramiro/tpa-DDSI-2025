package ar.utn.dssi.FuenteDinamica.dto.input;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDate;
import java.util.List;

@Data
@Getter
@Setter
public class HechoInputDTO {
  private String titulo;
  private String descripcion;
  private CategoriaInputDTO categoria;
  private Double latitud;
  private Double longitud;
  private LocalDate fechaAcontecimiento;
  private List<MultipartFile> contenidoMultimedia;
}