package ar.utn.dssi.FuenteEstatica.dto.input;

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
  private CategoriaInputDTO categoria;
  private UbicacionInputDTO ubicacion;
  private LocalDateTime fechaAcontecimiento;
}
