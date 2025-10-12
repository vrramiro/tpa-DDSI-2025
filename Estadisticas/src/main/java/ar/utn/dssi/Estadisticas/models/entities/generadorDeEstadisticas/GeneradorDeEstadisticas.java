package ar.utn.dssi.Estadisticas.models.entities.generadorDeEstadisticas;

import ar.utn.dssi.Estadisticas.models.entities.Estadistica;
import ar.utn.dssi.Estadisticas.models.entities.calculadores.ICalculadorDeEstadisticas;
import ar.utn.dssi.Estadisticas.models.entities.data.ContextoDeCalculo;
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
