package ar.utb.ba.dsi.Normalizador.models.mappers;

import ar.utb.ba.dsi.Normalizador.models.DTOs.Input.UbicacionInputDTOGeoref;
import ar.utb.ba.dsi.Normalizador.models.DTOs.Output.UbicacionOutputDTO;
import ar.utb.ba.dsi.Normalizador.models.entities.Ubicacion;
import lombok.Value;

public class MapperDeUbicacion {

    public static Ubicacion ubicacionFromInput(UbicacionInputDTOGeoref input) {
        Ubicacion ubicacion = new Ubicacion();
            ubicacion.setLatitud(input.getUbicacion().getLat());
            ubicacion.setLongitud(input.getUbicacion().getLon());
            ubicacion.setPais("Argentina");
            ubicacion.setProvincia(input.getUbicacion().getProvincia().getNombre());
            ubicacion.setCiudad(input.getUbicacion().getDepartamento().getNombre());
        return ubicacion;
    }

    public static UbicacionOutputDTO ubicacionOutputDTO(Ubicacion ubicacion) {
        UbicacionOutputDTO ubicacionOutputDTO = new UbicacionOutputDTO();
            ubicacionOutputDTO.setLatitud(ubicacion.getLatitud());
            ubicacionOutputDTO.setLongitud(ubicacion.getLongitud());
            ubicacionOutputDTO.setPais(ubicacion.getPais());
            ubicacionOutputDTO.setProvincia(ubicacion.getProvincia());
            ubicacionOutputDTO.setCiudad(ubicacion.getCiudad());
        return ubicacionOutputDTO;
    }

}
