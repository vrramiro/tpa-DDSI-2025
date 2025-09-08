package ar.utb.ba.dsi.estadisticas.models.entities.calculadores.impl;

import ar.utb.ba.dsi.estadisticas.models.entities.data.ContextoDeCalculo;
import ar.utb.ba.dsi.estadisticas.models.entities.Estadistica;
import ar.utb.ba.dsi.estadisticas.models.entities.TipoEstadistica;
import ar.utb.ba.dsi.estadisticas.models.entities.calculadores.ICalculadorDeEstadisticas;
import ar.utb.ba.dsi.estadisticas.models.entities.data.Coleccion;
import ar.utb.ba.dsi.estadisticas.models.entities.data.Hecho;

import java.time.LocalDateTime; import java.util.*;
import java.util.stream.Collectors;

public class ProvinciaConMasHechosColeccion implements ICalculadorDeEstadisticas {

  @Override
  public List<Estadistica> generarEstadistica(ContextoDeCalculo datos) {
    List<Coleccion> colecciones = datos.getColecciones();
    List<Estadistica> estadisticas = new ArrayList<>();
    Map<String, Long> hechosPorProvincia;

    for(Coleccion coleccion : colecciones) {
      hechosPorProvincia = coleccion.getHechos() .stream()
              .map(Hecho ::getProvincia)
              .collect(Collectors.groupingBy( provincia -> provincia, Collectors.counting()) );

      Map.Entry<String, Long> maxProvincia = hechosPorProvincia.entrySet().stream()
              .max(Map.Entry.comparingByValue()).orElse(null);

      if (maxProvincia != null) {
        Estadistica estadistica = Estadistica.builder()
                .coleccionId(coleccion.getId())
                .nombreColeccion(coleccion.getNombre())
                .tipo(TipoEstadistica.COLECCION_PROVINCIA_HECHOS)
                .valor(maxProvincia.getValue())
                .clave(maxProvincia.getKey())
                .fechaDeCalculo(LocalDateTime.now())
                .build();

        estadisticas.add(estadistica);
      }
    }
    return estadisticas;
  }
}