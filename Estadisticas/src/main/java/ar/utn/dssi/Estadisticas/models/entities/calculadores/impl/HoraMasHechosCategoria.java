package ar.utn.dssi.Estadisticas.models.entities.calculadores.impl;

import ar.utn.dssi.Estadisticas.models.entities.Estadistica;
import ar.utn.dssi.Estadisticas.models.entities.TipoEstadistica;
import ar.utn.dssi.Estadisticas.models.entities.calculadores.ICalculadorDeEstadisticas;
import ar.utn.dssi.Estadisticas.models.entities.data.Coleccion;
import ar.utn.dssi.Estadisticas.models.entities.data.ContextoDeCalculo;
import ar.utn.dssi.Estadisticas.models.entities.data.Hecho;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HoraMasHechosCategoria implements ICalculadorDeEstadisticas {

  @Override
  public List<Estadistica> generarEstadistica(ContextoDeCalculo datos) {
    List<Coleccion> colecciones = datos.getColecciones();        //ver
    List<Estadistica> estadisticas = new ArrayList<>();
    Map<LocalDateTime, Long> hechosPorHora;

    for (Coleccion coleccion : colecciones) {
      hechosPorHora = coleccion.getHechos().stream()
          .map(Hecho::getFechaAcontecimiento)
          .collect(Collectors.groupingBy(fechaAcontecimiento -> fechaAcontecimiento, Collectors.counting()));

      Map.Entry<LocalDateTime, Long> maxHora = hechosPorHora.entrySet().stream()
          .max(Map.Entry.comparingByValue()).orElse(null);

      if (maxHora != null) {
        Estadistica estadistica = Estadistica.builder()
            .coleccionId(coleccion.getId())
            .tipo(TipoEstadistica.CATEGORIA_HORA_HECHOS)
            .valor(maxHora.getValue())
            .clave(maxHora.getKey().toString())
            .fechaDeCalculo(LocalDateTime.now())
            .build();

        estadisticas.add(estadistica);
      }
    }
    return estadisticas;
  }

}

