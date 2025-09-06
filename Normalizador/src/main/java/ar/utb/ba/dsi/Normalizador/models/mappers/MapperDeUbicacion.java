package ar.utb.ba.dsi.Normalizador.models.mappers;

import ar.utb.ba.dsi.Normalizador.models.DTOs.Input.UbicacionInputDTOGeoref;
import ar.utb.ba.dsi.Normalizador.models.DTOs.Output.UbicacionOutputDTO;
import ar.utb.ba.dsi.Normalizador.models.entities.Ubicacion;

public class MapperDeUbicacion {
    public static Ubicacion ubicacionFromInput(UbicacionInputDTOGeoref input) {
        Ubicacion ubicacion = new Ubicacion();

        return ubicacion;
    }

    public static UbicacionOutputDTO ubicacionOutputDTO(Ubicacion ubicacion) {
        return null;
    }
}
