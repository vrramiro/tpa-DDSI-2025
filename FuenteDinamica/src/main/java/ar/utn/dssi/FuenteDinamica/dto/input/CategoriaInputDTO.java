package ar.utn.dssi.FuenteDinamica.dto.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CategoriaInputDTO {
  private Long id;
  @JsonProperty("categoria")
  private String nombreCategoria;
}
