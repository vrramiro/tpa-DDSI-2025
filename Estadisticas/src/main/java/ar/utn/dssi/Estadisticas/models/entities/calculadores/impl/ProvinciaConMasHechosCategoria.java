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

public class ProvinciaConMasHechosCategoria implements ICalculadorDeEstadisticas {

  @Override
  public List<Estadistica> generarEstadistica(ContextoDeCalculo datos) {
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