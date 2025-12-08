package ar.utn.dssi.app_web.dto.input;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class HechoRequest {
  private String titulo;
  private String descripcion;
  private Long idCategoria;
  private Double latitud;
  private Double longitud;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate fechaAcontecimiento;
  private List<String> urlsContenidoMultimedia;

  private Boolean anonimo;
}