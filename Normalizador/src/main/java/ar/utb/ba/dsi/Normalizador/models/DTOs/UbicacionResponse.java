package ar.utb.ba.dsi.Normalizador.models.DTOs;

import lombok.*;

@Data
@Getter
public class UbicacionResponse {
        private Double latitud;
        private Double longitud;
        private String pais;
        private String ciudad;
        private String provincia;
}
