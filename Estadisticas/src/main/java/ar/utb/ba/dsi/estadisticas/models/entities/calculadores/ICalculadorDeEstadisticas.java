package ar.utb.ba.dsi.estadisticas.models.entities.calculadores;

import ar.utb.ba.dsi.estadisticas.models.entities.data.ContextoDeCalculo;
import ar.utb.ba.dsi.estadisticas.models.entities.Estadistica;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ICalculadorDeEstadisticas {
  public List<Estadistica> generarEstadistica(ContextoDeCalculo datos);
}
