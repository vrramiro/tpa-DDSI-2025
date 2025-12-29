package ar.utn.dssi.FuenteDinamica.dto.input;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Data
@Getter
@Setter
public class HechoInputDTO {
  private String titulo;
  private String descripcion;
  private Long idCategoria;
  private Double latitud;
  private Double longitud;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate fechaAcontecimiento;
  private List<String> urlsContenidoMultimedia;

  private Boolean anonimo;
  private Boolean visible;
}