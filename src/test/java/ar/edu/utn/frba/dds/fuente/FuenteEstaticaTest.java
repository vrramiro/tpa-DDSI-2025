package ar.edu.utn.frba.dds.fuente;

import ar.edu.utn.frba.dds.contenido.Hecho;
import ar.edu.utn.frba.dds.fuente.Fuente;
import ar.edu.utn.frba.dds.fuente.FuenteEstatica;
import ar.edu.utn.frba.dds.fuente.ImportadorDeArchivos;
import ar.edu.utn.frba.dds.fuente.ImportadorDeArchivosCSV;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FuenteEstaticaTest {

  @Test
  public void seImportanCorrectamenteLosHechosDesdeUnCSV() {
    File archivo = new File("src/utils/datasets/desastres_tecnologicos_argentina.csv"); // Asegurate de ponerlo ahí
    ImportadorDeArchivos importador = new ImportadorDeArchivosCSV();
    Fuente fuente = new FuenteEstatica(importador, archivo);

    List<Hecho> hechos = fuente.obtenerHechos();

    // Verificaciones básicas
    assertNotNull(hechos, "La lista de hechos no debería ser null.");
    assertFalse(hechos.isEmpty(), "La lista de hechos no debería estar vacía.");

    // Verificar que al menos uno tenga datos válidos
    Hecho primerHecho = hechos.get(0);

    assertNotNull(primerHecho.getTitulo(), "El hecho debería tener título.");
    assertNotNull(primerHecho.getUbicacion(), "El hecho debería tener ubicación.");
    assertTrue(primerHecho.getFechaAcontecimiento().getYear() > 1900, "La fecha del hecho debería ser válida.");
  }
}