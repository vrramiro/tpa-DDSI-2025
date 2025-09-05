package ar.utb.ba.dsi.Normalizador.models.DTOs;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class UbicacionRequestGeoref {
    private Double latitud;
    private Double longitud;

}
