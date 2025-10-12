package ar.utn.dssi.Estadisticas.models.entities.generadorDeEstadisticas;

import ar.utn.dssi.Estadisticas.models.entities.Estadistica;
import ar.utn.dssi.Estadisticas.models.entities.data.ContextoDeCalculo;
import java.util.List;

public interface IGeneradorDeEstadisticas {
  public List<Estadistica> generarEstadisticas(ContextoDeCalculo contexto);
}
