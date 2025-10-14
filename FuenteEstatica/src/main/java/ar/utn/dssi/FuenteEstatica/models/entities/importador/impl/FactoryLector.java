package ar.utn.dssi.FuenteEstatica.models.entities.importador.impl;

import ar.utn.dssi.FuenteEstatica.error.ValidacionException;
import ar.utn.dssi.FuenteEstatica.models.entities.importador.HechoFactory;
import ar.utn.dssi.FuenteEstatica.models.entities.importador.ILectorDeArchivos;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.io.File;

@Component
@RequiredArgsConstructor
public class FactoryLector {

  private final HechoFactory hechoFactory;

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