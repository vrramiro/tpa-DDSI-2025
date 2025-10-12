package ar.utn.dssi.Estadisticas.models.entities.calculadores;

import ar.utn.dssi.Estadisticas.models.entities.Estadistica;
import ar.utn.dssi.Estadisticas.models.entities.data.ContextoDeCalculo;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public interface ICalculadorDeEstadisticas {
  public List<Estadistica> generarEstadistica(ContextoDeCalculo datos);
}
