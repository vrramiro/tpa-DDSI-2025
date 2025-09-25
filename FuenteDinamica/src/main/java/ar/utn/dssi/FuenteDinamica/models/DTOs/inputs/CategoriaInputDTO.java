package ar.utn.dssi.FuenteDinamica.models.DTOs.inputs;

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
