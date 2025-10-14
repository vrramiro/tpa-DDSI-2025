package ar.utn.dssi.Estadisticas.models.entities.generadorDeEstadisticas.impl;

import ar.utn.dssi.Estadisticas.models.entities.Estadistica;
import ar.utn.dssi.Estadisticas.models.entities.calculadores.ICalculadorDeEstadisticas;
import ar.utn.dssi.Estadisticas.models.entities.data.ContextoDeCalculo;
import ar.utn.dssi.Estadisticas.models.entities.generadorDeEstadisticas.IGeneradorDeEstadisticas;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class GeneradorDeEstadisticas implements IGeneradorDeEstadisticas {
  private final List<ICalculadorDeEstadisticas> calculadores;

  public GeneradorDeEstadisticas(List<ICalculadorDeEstadisticas> calculadores) {
    this.calculadores = calculadores;
  }

  @Override
  public List<Estadistica> generarEstadisticas(ContextoDeCalculo contexto) {
    return calculadores.stream()
        .flatMap(calculador -> calculador.generarEstadistica(contexto).stream())
        .toList();
  }
}
