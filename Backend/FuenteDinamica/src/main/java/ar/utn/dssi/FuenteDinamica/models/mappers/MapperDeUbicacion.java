package ar.utn.dssi.FuenteDinamica.models.mappers;


import ar.utn.dssi.FuenteDinamica.models.DTOs.inputs.UbicacionInputDTO;
import ar.utn.dssi.FuenteDinamica.models.entities.Ubicacion;

public class MapperDeUbicacion {
    public static Ubicacion ubicacionFromInput(UbicacionInputDTO input) {
        Ubicacion ubicacion = new Ubicacion();
            ubicacion.setLatitud(input.getLatitud());
            ubicacion.setLongitud(input.getLongitud());
            ubicacion.setPais(input.getPais());
            ubicacion.setProvincia(input.getProvincia());
            ubicacion.setCiudad(input.getCiudad());
        return ubicacion;
    }
}
