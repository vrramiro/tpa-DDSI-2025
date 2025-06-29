package ar.utn.dssi.Agregador.models.entities;

import ar.utn.dssi.Agregador.models.entities.criteriosDeFiltrado.ICriterioDeFiltrado;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
public class Filtro {
  List<ICriterioDeFiltrado> criteriosDeFiltro;

  public Boolean loCumple(Hecho hecho) {
    return this.criteriosDeFiltro
        .stream()
        .allMatch(criteriosDeFiltro -> criteriosDeFiltro.loCumple(hecho));
  }
}
