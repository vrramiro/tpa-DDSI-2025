package ar.utn.dssi.Agregador.models.entities;

import ar.utn.dssi.Agregador.models.entities.criteriosDeFiltrado.ICriterioDeFiltrado;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Filtro {
  List<ICriterioDeFiltrado> criteriosDeFiltro;

  public Boolean loCumple(Hecho hecho) {
    return this.criteriosDeFiltro
            .stream()
            .allMatch(criterio -> criterio.loCumple(hecho));
  }
}
