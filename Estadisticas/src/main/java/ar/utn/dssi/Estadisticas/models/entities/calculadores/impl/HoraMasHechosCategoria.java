package ar.utn.dssi.Estadisticas.models.entities.calculadores.impl;

import ar.utn.dssi.Estadisticas.models.entities.Estadistica;
import ar.utn.dssi.Estadisticas.models.entities.TipoEstadistica;
import ar.utn.dssi.Estadisticas.models.entities.calculadores.ICalculadorDeEstadisticas;
import ar.utn.dssi.Estadisticas.models.entities.data.Categoria;
import ar.utn.dssi.Estadisticas.models.entities.data.ContextoDeCalculo;
import ar.utn.dssi.Estadisticas.models.entities.data.Hecho;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class HoraMasHechosCategoria implements ICalculadorDeEstadisticas {

  @Override
  public List<Estadistica> generarEstadistica(ContextoDeCalculo datos) {
    List<Categoria> categorias = datos.getCategorias();
    List<Hecho> hechos = datos.getHechos();
    List<Estadistica> estadisticas = new ArrayList<>();

    for (Categoria categoria : categorias) {

      List<Hecho> hechosDeCategoria = hechos.stream()
          .filter(hecho -> hecho.getCategoria().equals(categoria.getNombre()))
          .toList();


      Map<Integer, Long> hechosPorHora = hechosDeCategoria.stream()
          .map(hecho -> hecho.getFechaAcontecimiento().getHour())
          .collect(Collectors.groupingBy(hora -> hora, Collectors.counting()));

      Map.Entry<Integer, Long> maxHora = hechosPorHora.entrySet().stream()
          .max(Map.Entry.comparingByValue()).orElse(null);

      if (maxHora != null) {
        Estadistica estadistica = Estadistica.builder()
            .categoriaId(categoria.getId())
            .nombreCategoria(categoria.getNombre())
            .tipo(TipoEstadistica.CATEGORIA_HORA_HECHOS)
            .valor(maxHora.getValue())
            .clave(maxHora.getKey().toString() + ":00 hs")
            .fechaDeCalculo(LocalDateTime.now())
            .build();

        estadisticas.add(estadistica);
      }
    }
    return estadisticas;
  }
}