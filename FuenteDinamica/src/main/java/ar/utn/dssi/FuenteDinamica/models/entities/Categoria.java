package ar.utn.dssi.FuenteDinamica.models.entities;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class Categoria {
   Long idCategoria;
   String nombre;
}
