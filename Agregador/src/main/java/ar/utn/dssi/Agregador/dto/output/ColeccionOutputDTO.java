package ar.utn.dssi.Agregador.dto.output;

import ar.utn.dssi.Agregador.models.entities.fuente.TipoFuente;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;
import java.util.Set;

@Data
public class ColeccionOutputDTO {
  @JsonProperty("handle")
  private String handle;
  @JsonProperty("titulo")
  private String titulo;
  @JsonProperty("descripcion")
  private String descripcion;
  @JsonProperty("criterios")
  private List<CriterioDePertenenciaOutputDTO> criterios;
  @JsonProperty("fuentes")
  private Set<TipoFuente> fuentes;
}