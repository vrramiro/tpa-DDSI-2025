package ar.utn.dssi.Estadisticas.models.entities.exportador.impl;

import ar.utn.dssi.Estadisticas.models.entities.Estadistica;
import ar.utn.dssi.Estadisticas.models.entities.exportador.IExportadorArchivos;
import ar.utn.dssi.Estadisticas.models.repositories.IEstadisticasRepository;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExportadorCSV implements IExportadorArchivos {

  private IEstadisticasRepository estadisticasReposotory;

  @Override
  public File exportarEstadisticas() {
    List<Estadistica> estadisticas = estadisticasReposotory.findAll();
    try {
      File temp = File.createTempFile("estadisticas", ".csv");
      try (FileWriter writer = new FileWriter(temp)) {
        writer.append("Tipo Estadistica,Valor,Clave,Fecha Calculo\n ");
        for (Estadistica estadistica : estadisticas) {
          writer.append(String.valueOf(estadistica.getTipo()))
              .append(",")
              .append(String.valueOf(estadistica.getValor()))
              .append(",")
              .append(String.valueOf(estadistica.getClave()))
              .append(",")
              .append(String.valueOf(estadistica.getFechaDeCalculo()))
              .append("\n");
        }
      }
      return temp;
    } catch (IOException e) {
      throw new RuntimeException("Error al exportar CSV", e);
    }
  }
}
