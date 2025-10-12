package ar.utn.dssi.Estadisticas.mappers;

import ar.utn.dssi.Estadisticas.dto.output.EstadisticaOutputDTO;
import ar.utn.dssi.Estadisticas.models.entities.Estadistica;

public class MapperDeEstadisticas {
  static public EstadisticaOutputDTO estadisticaOutputDTO(Estadistica estadistica) {
    EstadisticaOutputDTO estadisticaOutputDTO = new EstadisticaOutputDTO();

    estadisticaOutputDTO.setTipo(estadistica.getTipo());
    estadisticaOutputDTO.setNombreCategoria(estadistica.getNombreCategoria());
    estadisticaOutputDTO.setNombreColeccion(estadistica.getNombreColeccion());
    estadisticaOutputDTO.setValor(estadistica.getValor());
    estadisticaOutputDTO.setClave(estadistica.getClave());
    estadisticaOutputDTO.setFechaDeCalculo(estadistica.getFechaDeCalculo());

    return estadisticaOutputDTO;
  }
}
