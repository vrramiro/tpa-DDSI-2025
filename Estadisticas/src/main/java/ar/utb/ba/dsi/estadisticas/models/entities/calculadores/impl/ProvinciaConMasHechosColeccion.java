package ar.utb.ba.dsi.estadisticas.models.entities.calculadores.impl;

import ar.utb.ba.dsi.estadisticas.models.DTOs.inputDTOs.ColeccionInputDTO;
import ar.utb.ba.dsi.estadisticas.models.entities.DatosDeCalculo;
import ar.utb.ba.dsi.estadisticas.models.entities.Estadistica;
import ar.utb.ba.dsi.estadisticas.models.entities.TipoEstadistica;
import ar.utb.ba.dsi.estadisticas.models.entities.calculadores.IGeneradorDeEstadisticas;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProvinciaConMasHechosColeccion implements IGeneradorDeEstadisticas {
  @Override
  public List<Estadistica> generarEstadistica(DatosDeCalculo datos) {
    List<ColeccionInputDTO> colecciones = datos.getColecciones();

    List<Estadistica> estadisticas = new ArrayList<Estadistica>();

    /*
    for(ColeccionInputDTO coleccion : colecciones) {
      Map<String, Long> hechosPorProvincia = coleccion.getHechos().stream().Collectors.groupingBy(
        hecho -> hecho.getProvincia(),
        Collectors.counting()
      );

      estadisticas.add(Estadistica.builder()
          .coleccionId(coleccion.getId())
          .tipo(TipoEstadistica.COLECCION_PROVINCIA_HECHOS))
          .clave();
    }
    */
    return null;
  }
}
