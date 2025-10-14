package ar.utb.ba.dsi.Normalizador.dto.output;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Data
@Setter
@Getter
public class HechoOutputDTO {
  private String titulo;
  private String descripcion;
  private String tituloSanitizado;
  private String descripcionSanitizada;
  private CategoriaOutputDTO categoria;
  private UbicacionOutputDTO ubicacion;
  private LocalDateTime fechaAcontecimiento;
}

