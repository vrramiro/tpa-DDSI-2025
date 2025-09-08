package ar.utb.ba.dsi.estadisticas.models.entities.generadorDeEstadisticas;

import ar.utb.ba.dsi.estadisticas.models.entities.data.ContextoDeCalculo;
import ar.utb.ba.dsi.estadisticas.models.entities.Estadistica;
import ar.utb.ba.dsi.estadisticas.models.entities.calculadores.ICalculadorDeEstadisticas;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class GeneradorDeEstadisticas {
  private final List<ICalculadorDeEstadisticas> calculadores;

  public GeneradorDeEstadisticas(List<ICalculadorDeEstadisticas> calculadores) {
    this.calculadores = calculadores;
  }

  public List<Estadistica> generarEstadisticas(ContextoDeCalculo contexto) {
    return calculadores.stream()
        .flatMap(calculador -> calculador.generarEstadistica(contexto).stream())
        .toList();
  }
}
