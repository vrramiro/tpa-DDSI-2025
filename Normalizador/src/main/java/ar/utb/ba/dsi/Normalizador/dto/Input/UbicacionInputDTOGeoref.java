package ar.utb.ba.dsi.Normalizador.dto.Input;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class UbicacionInputDTOGeoref {
  private Ubicacion ubicacion;

  @Data
  public static class Ubicacion {
    @JsonProperty("departamento_nombre")
    private String departamento;
    @JsonProperty("provincia_nombre")
    private String provincia;
    @JsonProperty("municipio_nombre")
    private String municipio;
    @JsonProperty("lat")
    private Double lat;
    @JsonProperty("lon")
    private Double lon;
  }
}