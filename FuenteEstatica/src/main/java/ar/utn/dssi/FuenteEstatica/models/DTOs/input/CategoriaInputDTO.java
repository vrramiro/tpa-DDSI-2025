package ar.utn.dssi.FuenteEstatica.models.DTOs.input;

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
