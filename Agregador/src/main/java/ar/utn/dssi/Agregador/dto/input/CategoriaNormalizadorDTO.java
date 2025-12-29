package ar.utn.dssi.Agregador.dto.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CategoriaNormalizadorDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("categoria")
    private String nombre;
}