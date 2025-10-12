package ar.utn.dssi.FuenteEstatica.dto.input;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CategoriaInputDTO {
  private Long id;
  private String categoria;
}
