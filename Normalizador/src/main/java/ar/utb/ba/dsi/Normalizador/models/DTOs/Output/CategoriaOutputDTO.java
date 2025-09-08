package ar.utb.ba.dsi.Normalizador.models.DTOs.Output;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class CategoriaOutputDTO {
    private Long id;
    private String categoria;
}
