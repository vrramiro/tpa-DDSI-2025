package ar.utn.dssi.FuenteEstatica.dto.output;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class HechoOutputDTONormalizador {
  private String titulo;
  private String descripcion;
  private String categoria;
  private Double latitud;
  private Double longitud;
  private String fechaAcontecimiento;
}
