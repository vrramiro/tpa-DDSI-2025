package ar.utn.dssi.FuenteProxy.mappers;


import ar.utn.dssi.FuenteProxy.dto.input.UbicacionInputDTONormalizador;
import ar.utn.dssi.FuenteProxy.models.entities.Ubicacion;

public class MapperDeUbicacion {
  public static Ubicacion ubicacionFromInput(UbicacionInputDTONormalizador input) {
    Ubicacion ubicacion = new Ubicacion();
    ubicacion.setLatitud(input.getLatitud());
    ubicacion.setLongitud(input.getLongitud());
    ubicacion.setPais(input.getPais());
    ubicacion.setProvincia(input.getProvincia());
    ubicacion.setCiudad(input.getCiudad());
    return ubicacion;
  }
}
