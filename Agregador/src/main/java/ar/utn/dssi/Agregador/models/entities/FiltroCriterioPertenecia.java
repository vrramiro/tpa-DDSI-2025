package ar.utn.dssi.Agregador.models.entities;

import ar.utn.dssi.Agregador.models.entities.criteriosDePertenencia.CriterioDePertenencia;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FiltroCriterioPertenecia { //TODO: NO ESTA RELACIONADO AL FILTRADO, SINO AL CRITERIO
  List<CriterioDePertenencia> criteriosDeFiltro;

  public Boolean loCumple(Hecho hecho) {
    return this.criteriosDeFiltro
        .stream()
        .allMatch(criterio -> criterio.loCumple(hecho));
  }
}
