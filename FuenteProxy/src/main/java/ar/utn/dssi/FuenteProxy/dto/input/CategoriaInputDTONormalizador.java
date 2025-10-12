package ar.utn.dssi.FuenteProxy.dto.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CategoriaInputDTONormalizador {
    @JsonProperty("id") private Long idCategoria;
    @JsonProperty("categoria") private String nombre;
}
