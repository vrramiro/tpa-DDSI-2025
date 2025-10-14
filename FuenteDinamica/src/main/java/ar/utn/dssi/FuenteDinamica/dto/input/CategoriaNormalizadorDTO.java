package ar.utn.dssi.FuenteDinamica.dto.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CategoriaNormalizadorDTO {
  @JsonProperty("id")
  private Long idCategoria;
  @JsonProperty("categoria")
  private String nombre;
}
