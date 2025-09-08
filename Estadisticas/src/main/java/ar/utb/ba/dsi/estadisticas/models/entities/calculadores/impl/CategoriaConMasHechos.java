package ar.utb.ba.dsi.estadisticas.models.entities.calculadores.impl;

import ar.utb.ba.dsi.estadisticas.models.DTOs.inputDTOs.CategoriaInputDTO;
import ar.utb.ba.dsi.estadisticas.models.entities.*;
import ar.utb.ba.dsi.estadisticas.models.entities.calculadores.IGeneradorDeEstadisticas;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CategoriaConMasHechos implements IGeneradorDeEstadisticas {
  @Override
  public List<Estadistica> generarEstadistica(DatosDeCalculo datos) {
    List<Categoria> categorias = datos.getCategorias();
    List<Estadistica> estadisticas = new ArrayList<>();

    Map<String, Long> hechosPorCategoria = datos.getHechos().stream()
            .collect(Collectors.groupingBy(Hecho::getCategoria, Collectors.counting()));

    Map.Entry<String, Long> entradaCategoriaMaxima = hechosPorCategoria.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .orElse(null);

    Categoria categoriaMaxima = null;

    if(entradaCategoriaMaxima != null) {
      categoriaMaxima = categorias.stream().filter(categoria -> categoria.getNombre()
              .equals(entradaCategoriaMaxima.getKey()))
              .findFirst()
              .orElse(null);

      Estadistica estadisticaObtenida = Estadistica.builder()
              .categoriaId(categoriaMaxima.getId())
              .clave(categoriaMaxima.getNombre())
              .valor(entradaCategoriaMaxima.getValue())
              .tipo(TipoEstadistica.CATEGORIA_MAS_HECHOS)
              .fechaDeCalculo(LocalDateTime.now())
              .build();

        estadisticas.add(estadisticaObtenida);
    }

    return estadisticas;
  }
}
