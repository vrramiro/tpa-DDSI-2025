package ar.utn.dssi.Estadisticas.mappers;

import ar.utn.dssi.Estadisticas.dto.input.ColeccionInputDTO;
import ar.utn.dssi.Estadisticas.models.entities.data.Coleccion;

public class MapperDeColecciones {
  public static Coleccion coleccionFromInputDTO(ColeccionInputDTO coleccionInputDTO) {
    Coleccion coleccion = new Coleccion();
    coleccion.setHandle(coleccionInputDTO.getHandle());
    coleccion.setNombre(coleccionInputDTO.getTitulo());
    coleccion.setHechos(
        coleccionInputDTO
            .getHechos()
            .stream()
            .map(MapperDeHechos::hechoFromInput)
            .toList()
    );

    return coleccion;
  }
}
