package ar.utn.dssi.FuenteDinamica.dto.input;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class HechoInputDTONormalizador {
  private String titulo;
  private String descripcion;
  private String tituloSanitizado;
  private String descripcionSanitizada;
  private CategoriaNormalizadorDTO categoria;
  private UbicacionInputDTO ubicacion;
  private LocalDateTime fechaAcontecimiento;

  private Boolean anonimo;
  private Boolean visible;
}
