package ar.utb.ba.dsi.estadisticas.models.mappers;

import ar.utb.ba.dsi.estadisticas.models.DTOs.outputs.EstadisticaOutputDTO;
import ar.utb.ba.dsi.estadisticas.models.entities.Estadistica;

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
