package ar.utn.dssi.Estadisticas.models.entities.exportador.impl;

import ar.utn.dssi.Estadisticas.models.entities.exportador.IExportadorArchivos;
import ar.utn.dssi.Estadisticas.models.entities.TipoArchivo;

public class ExportadorFactory {
   public static IExportadorArchivos getExportador(TipoArchivo tipo) {
       IExportadorArchivos importador = null;
       switch (tipo) {
           case CSV -> importador = new ExportadorCSV();
           default -> throw new IllegalArgumentException("Tipo de archivo no soportado: " + tipo);
       }
       return importador;
   }
}
