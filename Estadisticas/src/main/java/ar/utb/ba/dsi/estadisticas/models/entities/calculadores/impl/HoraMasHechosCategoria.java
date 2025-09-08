package ar.utb.ba.dsi.estadisticas.models.entities.calculadores.impl;

import ar.utb.ba.dsi.estadisticas.models.DTOs.inputDTOs.ColeccionInputDTO;
import ar.utb.ba.dsi.estadisticas.models.DTOs.inputDTOs.HechoInputDTO;
import ar.utb.ba.dsi.estadisticas.models.DTOs.inputDTOs.UbicacionInputDTO;
import ar.utb.ba.dsi.estadisticas.models.entities.DatosDeCalculo;
import ar.utb.ba.dsi.estadisticas.models.entities.Estadistica;
import ar.utb.ba.dsi.estadisticas.models.entities.TipoEstadistica;
import ar.utb.ba.dsi.estadisticas.models.entities.calculadores.IGeneradorDeEstadisticas;
import ar.utb.ba.dsi.estadisticas.models.entities.Coleccion;
import ar.utb.ba.dsi.estadisticas.models.entities.Hecho;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HoraMasHechosCategoria implements IGeneradorDeEstadisticas {

  @Override
  public List<Estadistica> generarEstadistica(DatosDeCalculo datos) {
    List<Coleccion> colecciones = datos.getColecciones();        //ver
    List<Estadistica> estadisticas = new ArrayList<>();
    Map<LocalDateTime, Long> hechosPorHora;

    for(Coleccion coleccion : colecciones) {
      hechosPorHora = coleccion.getHechos().stream()
              .map(Hecho:: getFechaAcontecimiento)
              .collect(Collectors.groupingBy(fechaAcontecimiento -> fechaAcontecimiento, Collectors.counting()) );

      Map.Entry<LocalDateTime, Long> maxHora = hechosPorHora.entrySet().stream()
              .max(Map.Entry.comparingByValue()).orElse(null);

      if (maxHora != null) {
        Estadistica estadistica = Estadistica.builder().coleccionId(coleccion.getId())
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

