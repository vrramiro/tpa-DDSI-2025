package ar.utn.dssi.FuenteDinamica.mappers;


import ar.utn.dssi.FuenteDinamica.dto.input.UbicacionInputDTO;
import ar.utn.dssi.FuenteDinamica.dto.output.UbicacionOutputDTO;
import ar.utn.dssi.FuenteDinamica.dto.output.UbicacionOutputDTONormalizador;
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

  public static UbicacionOutputDTONormalizador ubicacionFromLatitudYLongitud(Double latitud, Double longitud) {
    UbicacionOutputDTONormalizador ubicacionOutputDTO = new UbicacionOutputDTONormalizador();
    ubicacionOutputDTO.setLatitud(latitud);
    ubicacionOutputDTO.setLongitud(longitud);
    return ubicacionOutputDTO;
  }

  public static UbicacionOutputDTO ubicacionOuputFromUbicacion(Ubicacion ubicacion) {
    UbicacionOutputDTO ubicacionOutputDTO = new UbicacionOutputDTO();
    ubicacionOutputDTO.setLatitud(ubicacion.getLatitud());
    ubicacionOutputDTO.setLongitud(ubicacion.getLongitud());
    ubicacionOutputDTO.setPais(ubicacion.getPais());
    ubicacionOutputDTO.setProvincia(ubicacion.getProvincia());
    ubicacionOutputDTO.setCiudad(ubicacion.getCiudad());
    return ubicacionOutputDTO;
  }
}
