package ar.utn.dssi.Estadisticas.mappers;

import ar.utn.dssi.Estadisticas.dto.input.ColeccionInputDTO;
import ar.utn.dssi.Estadisticas.dto.input.HechoInputDTO;
import ar.utn.dssi.Estadisticas.models.entities.data.Coleccion;
import java.util.Collections;
import java.util.List;

public class MapperDeColecciones {
  public static Coleccion coleccionFromInputDTO(ColeccionInputDTO coleccionInputDTO) {
    Coleccion coleccion = new Coleccion();
    coleccion.setHandle(coleccionInputDTO.getHandle());
    coleccion.setNombre(coleccionInputDTO.getTitulo());

    List<HechoInputDTO> listaHechos = coleccionInputDTO.getHechos() != null
            ? coleccionInputDTO.getHechos()
            : Collections.emptyList();

    coleccion.setHechos(
        listaHechos
            .stream()
            .map(MapperDeHechos::hechoFromInput)
            .toList()
    );

    return coleccion;
  }
}
