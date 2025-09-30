package ar.utn.dssi.FuenteEstatica.models.mappers;


import ar.utn.dssi.FuenteEstatica.models.DTOs.input.UbicacionInputDTO;
import ar.utn.dssi.FuenteEstatica.models.entities.contenido.Ubicacion;

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
