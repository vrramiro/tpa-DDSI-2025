package ar.utn.dssi.Estadisticas.models.entities.exportador;

import ar.utn.dssi.Estadisticas.models.entities.Estadistica;

import java.io.File;
import java.util.List;

public interface IExportadorArchivos {
  File exportarEstadisticas(List<Estadistica> estadisticas);
}
