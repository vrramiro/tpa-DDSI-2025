package ar.utb.ba.dsi.estadisticas.models.entities.calculadores.impl;

import ar.utb.ba.dsi.estadisticas.models.DTOs.inputDTOs.CategoriaInputDTO;
import ar.utb.ba.dsi.estadisticas.models.DTOs.inputDTOs.HechoInputDTO;
import ar.utb.ba.dsi.estadisticas.models.DTOs.inputDTOs.UbicacionInputDTO;
import ar.utb.ba.dsi.estadisticas.models.entities.*;
import ar.utb.ba.dsi.estadisticas.models.entities.calculadores.IGeneradorDeEstadisticas;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProvinciaConMasHechosCategoria implements IGeneradorDeEstadisticas {

  @Override
  public List<Estadistica> generarEstadistica(DatosDeCalculo datos) {
    List<Categoria> categorias = datos.getCategorias();
    List<Hecho> hechos = datos.getHechos();
    List<Estadistica> estadisticas = new ArrayList<>();

    for (Categoria categoria : categorias) {
      List<Hecho> hechosDeCategoria = hechos.stream()
              .filter(hecho -> hecho.getCategoria().equals(categoria.getNombre()))
              .toList();

      Map<String, Long> hechosPorProvincia = hechosDeCategoria.stream()
              .map(Hecho::getProvincia)
              .collect(Collectors.groupingBy(
                      provincia -> provincia,
                      Collectors.counting()
              ));

        Map.Entry<String, Long> maxProvincia = hechosPorProvincia.entrySet()
                .stream().max(Map.Entry.comparingByValue()).orElse(null);

        if (maxProvincia != null) {

        Estadistica estadistica = Estadistica.builder()
                .categoriaId(categoria.getId())
                .nombreCategoria(categoria.getNombre())
                .tipo(TipoEstadistica.CATEGORIA_PROVINCIA_HECHOS)
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