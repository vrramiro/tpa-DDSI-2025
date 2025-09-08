package ar.utb.ba.dsi.estadisticas.models.entities.calculadores;

import ar.utb.ba.dsi.estadisticas.models.entities.DatosDeCalculo;
import ar.utb.ba.dsi.estadisticas.models.entities.Estadistica;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface IGeneradorDeEstadisticas {
  public List<Estadistica> generarEstadistica(DatosDeCalculo datos);
}
