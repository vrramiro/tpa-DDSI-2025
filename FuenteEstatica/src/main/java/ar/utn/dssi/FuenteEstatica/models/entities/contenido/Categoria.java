package ar.utn.dssi.FuenteEstatica.models.entities.contenido;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Categoria {
    private Long idCategoria;
    private String categoria;
}
