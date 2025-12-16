package ar.utn.dssi.Estadisticas.models.entities.exportador.impl;

import ar.utn.dssi.Estadisticas.models.entities.Estadistica;
import ar.utn.dssi.Estadisticas.models.entities.TipoEstadistica;
import ar.utn.dssi.Estadisticas.models.entities.exportador.IExportadorArchivos;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ExportadorCSV implements IExportadorArchivos {

  private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

  @Override
  public File exportarEstadisticas(List<Estadistica> estadisticas) {
    try {
      File temp = File.createTempFile("estadisticas", ".csv");
      try (FileWriter writer = new FileWriter(temp)) {
        writer.append("Tipo de Estadística;Colección;Categoría;Concepto Clave;Valor;Fecha de Cálculo\n ");
        for (Estadistica estadistica : estadisticas) {
          writer.append(limpiarTexto(formatearTipo(estadistica.getTipo()))).append(";")
                  .append(limpiarTexto(estadistica.getNombreColeccion() != null ? estadistica.getNombreColeccion() : "-")).append(";")
                  .append(limpiarTexto(estadistica.getNombreCategoria() != null ? estadistica.getNombreCategoria() : "-")).append(";")
                  .append(limpiarTexto(estadistica.getClave())).append(";")
                  .append(String.valueOf(estadistica.getValor())).append(";")
                  .append(estadistica.getFechaDeCalculo().format(FORMATO_FECHA))
                  .append("\n");
        }
      }
      return temp;
    } catch (IOException e) {
      throw new RuntimeException("Error al exportar CSV", e);
    }
  }

  private String formatearTipo(TipoEstadistica tipo) {
    if (tipo == null) return "";
    return tipo.name().replace("_", " ").toLowerCase();
    // Opcional: podrías meter una lógica para capitalizar la primera letra de cada palabra
  }

  private String limpiarTexto(String texto) {
    if (texto == null) return "";
    return texto.replace(";", ",");
  }
}
