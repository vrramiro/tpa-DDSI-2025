package ar.utb.ba.dsi.estadisticas.models.entities.generadorDeEstadisticas;

import ar.utb.ba.dsi.estadisticas.models.entities.data.ContextoDeCalculo;
import ar.utb.ba.dsi.estadisticas.models.entities.Estadistica;
import java.util.List;

public interface IGeneradorDeEstadisticas {
  public List<Estadistica> generarEstadisticas(ContextoDeCalculo contexto);
}
