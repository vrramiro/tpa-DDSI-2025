package ar.utn.dssi.Estadisticas.models.mappers;


import ar.utn.dssi.Estadisticas.models.DTOs.inputs.HechoInputDTO;
import ar.utn.dssi.Estadisticas.models.entities.data.Hecho;

public class MapperDeHechos {


  public static Hecho hechoFromInput(HechoInputDTO hechoInputDTO) {
    Hecho hecho = new Hecho();
    hecho.setProvincia(hechoInputDTO.getUbicacion().getProvincia());
    hecho.setCategoria(hechoInputDTO.getCategoria());
    hecho.setFechaAcontecimiento(hechoInputDTO.getFechaAcontecimiento());
    return null;
  }


}
