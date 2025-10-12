package ar.utn.dssi.FuenteEstatica.models.mappers;


import ar.utn.dssi.FuenteEstatica.models.DTOs.input.UbicacionInputDTO;
import ar.utn.dssi.FuenteEstatica.models.DTOs.output.UbicacionOutputDTO;
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

  public static UbicacionOutputDTO OutputFromUbicacion(Ubicacion ubicacion) {
    UbicacionOutputDTO ubicacionOutputDTO = new UbicacionOutputDTO();
    ubicacionOutputDTO.setLatitud(ubicacion.getLatitud());
    ubicacionOutputDTO.setLongitud(ubicacion.getLongitud());
    ubicacionOutputDTO.setPais(ubicacion.getPais());
    ubicacionOutputDTO.setCiudad(ubicacion.getCiudad());
    ubicacionOutputDTO.setProvincia(ubicacion.getProvincia());
    return ubicacionOutputDTO;
  }
}
