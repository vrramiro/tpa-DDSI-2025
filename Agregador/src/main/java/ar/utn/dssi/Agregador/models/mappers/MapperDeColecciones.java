package ar.utn.dssi.Agregador.models.mappers;

import ar.utn.dssi.Agregador.models.DTOs.outputDTO.ColeccionOutputDTO;
import ar.utn.dssi.Agregador.models.entities.Coleccion;

public class MapperDeColecciones {
  public static ColeccionOutputDTO coleccionOutputDTOFromColeccion(Coleccion coleccion) {
    var coleccionDto = new ColeccionOutputDTO();

    coleccionDto.setTitulo(coleccion.getTitulo());
    coleccionDto.setDescripcion(coleccion.getDescripcion());
    coleccionDto.setHechos(coleccion.getHechos());

    return coleccionDto;
  }
}
