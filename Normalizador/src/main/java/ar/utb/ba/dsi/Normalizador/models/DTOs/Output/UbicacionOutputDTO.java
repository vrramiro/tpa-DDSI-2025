package ar.utb.ba.dsi.Normalizador.models.DTOs.Output;

import lombok.*;

@Data
@Getter
public class UbicacionOutputDTO {
        private Double latitud;
        private Double longitud;
        private String pais;
        private String ciudad;
        private String provincia;
}
