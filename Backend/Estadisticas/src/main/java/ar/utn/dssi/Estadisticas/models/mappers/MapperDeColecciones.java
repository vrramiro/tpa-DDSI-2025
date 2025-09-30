package ar.utn.dssi.Estadisticas.models.mappers;

import ar.utn.dssi.Estadisticas.models.DTOs.inputs.ColeccionInputDTO;
import ar.utn.dssi.Estadisticas.models.entities.data.Coleccion;
import ar.utn.dssi.Estadisticas.models.entities.data.Hecho;

import java.util.List;

public class MapperDeColecciones {
    public static Coleccion coleccionFromInputDTO(ColeccionInputDTO coleccionInputDTO) {
        Coleccion coleccion = new Coleccion();
            coleccion.setId(coleccionInputDTO.getId());
            coleccion.setNombre(coleccionInputDTO.getTitulo());
            coleccion.setHechos(
                    coleccionInputDTO
                            .getHechos()
                            .stream()
                            .map(MapperDeHechos :: hechoFromInput)
                            .toList()
            );

            return coleccion;
    }
}
