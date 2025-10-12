package ar.utn.dssi.FuenteEstatica.models.entities.importador.impl;

import ar.utn.dssi.FuenteEstatica.models.entities.importador.HechoFactory;
import ar.utn.dssi.FuenteEstatica.models.entities.importador.ILectorDeArchivos;
import ar.utn.dssi.FuenteEstatica.models.errores.ValidacionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.File;

@Component
public class FactoryLector {

  private final HechoFactory hechoFactory;

  @Autowired
  public FactoryLector(HechoFactory hechoFactory) {
    this.hechoFactory = hechoFactory;
  }

  private static String obtenerExtension(String nombreArchivo) {
    int punto = nombreArchivo.lastIndexOf('.');
    if (punto != -1 && punto < nombreArchivo.length() - 1) {
      return nombreArchivo.substring(punto + 1).toLowerCase();
    }
    return "";
  }

  public ILectorDeArchivos crearLector(File archivo) {
    String extension = obtenerExtension(archivo.getName());

    switch (extension) {
      case "csv":
        return new LectorDeArchivosCSV(hechoFactory);
      default:
        throw new ValidacionException("No se ha podido crear el lector de archivo con extensión: " + extension);
    }
  }
}