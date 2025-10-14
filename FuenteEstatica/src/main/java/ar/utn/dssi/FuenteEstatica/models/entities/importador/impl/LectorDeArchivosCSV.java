package ar.utn.dssi.FuenteEstatica.models.entities.importador.impl;

import ar.utn.dssi.FuenteEstatica.models.entities.contenido.Hecho;
import ar.utn.dssi.FuenteEstatica.models.entities.importador.HechoFactory;
import ar.utn.dssi.FuenteEstatica.models.entities.importador.ILectorDeArchivos;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class LectorDeArchivosCSV implements ILectorDeArchivos {
  private final HechoFactory hechoFactory;

  public LectorDeArchivosCSV(HechoFactory hechoFactory) {
    this.hechoFactory = hechoFactory;
  }

  @Override
  public List<Hecho> importarHechos(File archivo) {
    List<Hecho> hechos = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(archivo), StandardCharsets.UTF_8))) {
      String linea;
      boolean esPrimeraLinea = true;

      while ((linea = reader.readLine()) != null) {
        if (esPrimeraLinea) {
          esPrimeraLinea = false; // saltar encabezado
          continue;
        }

        Hecho hechoObtenido = hechoFactory.crearHecho(linea);

        if (hechoObtenido == null) {
          throw new RuntimeException("No se pudo obtener el hecho, faltan campos.");
        }
        hechos.add(hechoObtenido);

      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return hechos;
  }
}