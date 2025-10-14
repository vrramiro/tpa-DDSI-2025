package ar.utn.dssi.Agregador.dto.output;

import ar.utn.dssi.Agregador.models.entities.criteriosDePertenencia.TipoCriterio;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CriterioDePertenenciaOutputDTO {
  @JsonProperty("id_criterio")
  private Long id;
  @JsonProperty("tipo_criterio")
  private TipoCriterio tipoCriterio;
  @JsonProperty("valor")
  private String valor;
}
