package ar.utb.ba.dsi.Normalizador.models.DTOs.Input;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

/*
{
  "parametros": {
    "aplanar": true,
    "campos": [
      "lat",
      "municipio.nombre",
      "lon",
      "departamento.nombre",
      "provincia.nombre",
      "provincia.id"
    ],
    "formato": "json",
    "lat": -31.133566,
    "lon": -64.433583
  },
  "ubicacion": {
    "departamento_nombre": "Punilla",
    "lat": -31.133566,
    "lon": -64.433583,
    "municipio_nombre": "Valle Hermoso",
    "provincia_id": "14",
    "provincia_nombre": "Córdoba"
  }
}
* */

@Data
@Getter
public class UbicacionInputDTOGeoref {
    private Ubicacion ubicacion;
    @Data public static class Ubicacion {
        @JsonProperty("departamento_nombre") private String departamento;
        @JsonProperty("provincia_nombre") private String provincia;
        @JsonProperty("municipio_nombre") private String municipio;
        @JsonProperty("lat") private Double lat;
        @JsonProperty("lon") private Double lon;
    }

    /*
    @Data
    public static class UbicacionInner {
        private Departamento departamento;
        private GobiernoLocal gobierno_local;
        private Double lat;
        private Double lon;
        private Provincia provincia;
    }

    @Data
    public static class Departamento {
        private String id;
        private String nombre;
    }

    @Data
    public static class GobiernoLocal {
        private String id;
        private String nombre;
    }

    @Data
    public static class Provincia {
        private String id;
        private String nombre;
    }
    */
}