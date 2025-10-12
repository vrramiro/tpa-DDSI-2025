package ar.utb.ba.dsi.Normalizador.models.mappers;

import ar.utb.ba.dsi.Normalizador.models.DTOs.Input.UbicacionInputDTOGeoref;
import ar.utb.ba.dsi.Normalizador.models.DTOs.Output.UbicacionOutputDTO;
import ar.utb.ba.dsi.Normalizador.models.entities.Ubicacion;

public class MapperDeUbicacion {
    public static Ubicacion ubicacionFromInput(UbicacionInputDTOGeoref input) {
        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setLatitud(input.getUbicacion().getLat());
        ubicacion.setLongitud(input.getUbicacion().getLon());
        ubicacion.setPais("Argentina");
        ubicacion.setProvincia(input.getUbicacion().getProvincia());

        String municipio = input.getUbicacion().getMunicipio();
        String departamento = input.getUbicacion().getDepartamento();

        if (municipio != null && !municipio.isEmpty()) {
            ubicacion.setCiudad(municipio);
        } else {
            ubicacion.setCiudad(departamento);
        }

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
