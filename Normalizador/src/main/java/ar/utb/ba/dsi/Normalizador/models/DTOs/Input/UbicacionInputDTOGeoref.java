package ar.utb.ba.dsi.Normalizador.models.DTOs.Input;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class UbicacionInputDTOGeoref {
    private UbicacionInner ubicacion;

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
}