package ar.utn.dssi.app_web.DTO.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CategoriaInputDTO {
    private Long id;
    @JsonProperty("categoria") private String nombreCategoria;
}
