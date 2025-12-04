package ar.utn.dssi.app_web.dto.Criterio;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CriterioDTO {
    @JsonProperty("tipo_criterio")
    private TipoCriterio tipo;

    private String valor;
}
