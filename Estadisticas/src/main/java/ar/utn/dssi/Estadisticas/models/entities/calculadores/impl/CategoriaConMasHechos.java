package ar.utn.dssi.Estadisticas.models.entities.calculadores.impl;

import ar.utn.dssi.Estadisticas.models.entities.Estadistica;
import ar.utn.dssi.Estadisticas.models.entities.TipoEstadistica;
import ar.utn.dssi.Estadisticas.models.entities.calculadores.ICalculadorDeEstadisticas;
import ar.utn.dssi.Estadisticas.models.entities.data.Categoria;
import ar.utn.dssi.Estadisticas.models.entities.data.ContextoDeCalculo;
import ar.utn.dssi.Estadisticas.models.entities.data.Hecho;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CategoriaConMasHechos implements ICalculadorDeEstadisticas {
  @Override
  public List<Estadistica> generarEstadistica(ContextoDeCalculo datos) {
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
