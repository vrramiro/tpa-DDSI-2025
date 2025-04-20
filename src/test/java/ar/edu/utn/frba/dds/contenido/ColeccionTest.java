package ar.edu.utn.frba.dds.contenido;

import static org.junit.jupiter.api.Assertions.*;
import ar.edu.utn.frba.dds.contenido.*;
import ar.edu.utn.frba.dds.criterio.*;
import ar.edu.utn.frba.dds.fuente.Fuente;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ColeccionTest {
  private Coleccion coleccion;
  private List<Hecho> hechosDePrueba;
  private Fuente fuenteMock;

  private Categoria categoriaAeronave = new Categoria("Caída de aeronave");
  private Categoria categoriaMaquinaria = new Categoria("Accidente con maquinaria industrial");
  private Categoria categoriaPasoNivel = new Categoria("Accidente en paso a nivel");
  private Categoria categoriaDerrumbe = new Categoria("Derrumbe en obra en construcción");

  @BeforeEach
  public void setUp() {
    // Crear hechos
    hechosDePrueba = List.of(
        crearHecho("Caída de aeronave impacta en Olavarría", categoriaAeronave, LocalDateTime.of(2001, 11, 29, 0, 0)),
        crearHecho("Serio incidente: Accidente con maquinaria industrial en Chos Malal, Neuquén", categoriaMaquinaria, LocalDateTime.of(2001, 8, 16, 0, 0)),
        crearHecho("Caída de aeronave impacta en Venado Tuerto, Santa Fe", categoriaAeronave, LocalDateTime.of(2008, 8, 8, 0, 0)),
        crearHecho("Accidente en paso a nivel deja múltiples daños en Pehuajó", categoriaPasoNivel, LocalDateTime.of(2020, 1, 27, 0, 0)),
        crearHecho("Devastador Derrumbe en obra en construcción en Roque Sáenz Peña", categoriaDerrumbe, LocalDateTime.of(2016, 6, 4, 0, 0))
    );

    // Mock de fuente que devuelve estos hechos
    fuenteMock = () -> hechosDePrueba;

    coleccion = new Coleccion();
    coleccion.setTitulo("Colección prueba");
    coleccion.setDescripcion("Esto es una prueba");
    coleccion.setFuenteDeOrigen(fuenteMock);
    coleccion.setCriteriosDePertenecias(new ArrayList<>());
  }

  @Test
  public void testCargarHechosSinCriterios() {
    coleccion.cargarHechos();
    assertEquals(5, coleccion.getHechos().size());
  }

  @Test
  public void testCargarHechosConCriterioFecha() {
    coleccion.getCriteriosDePertenecias().add(new CriterioPorFecha(
        LocalDateTime.of(2000, 1, 1, 0, 0),
        LocalDateTime.of(2010, 1, 1, 0, 0)));

    coleccion.cargarHechos();
    assertEquals(3, coleccion.getHechos().size());
  }

  @Test
  public void testCargarHechosConCriterioFechaYCategoria() {
    coleccion.getCriteriosDePertenecias().add(new CriterioPorFecha(
        LocalDateTime.of(2000, 1, 1, 0, 0),
        LocalDateTime.of(2010, 1, 1, 0, 0)));
    coleccion.getCriteriosDePertenecias().add(new CriterioPorCategoria(categoriaAeronave));

    coleccion.cargarHechos();
    assertEquals(2, coleccion.getHechos().size());
  }

  @Test
  public void testFiltroQueNoDevuelveNada() {
    coleccion.cargarHechos();
    List<Hecho> filtrados = coleccion.getHechos().stream()
        .filter(h -> h.getCategoria().getNombre().equals("Caída de Aeronave")) // distinto por mayúscula
        .filter(h -> h.getTitulo().equals("un título"))
        .toList();

    assertTrue(filtrados.isEmpty());
  }

  @Test
  public void testEtiquetadoDeHecho() {
    Hecho hecho = hechosDePrueba.get(0); // Olavarría
    hecho.getEtiquetas().add(new Etiqueta("Olavarría"));
    hecho.getEtiquetas().add(new Etiqueta("Grave"));

    List<String> nombresEtiquetas = hecho.getEtiquetas().stream().map(Etiqueta::getNombre).toList();
    assertTrue(nombresEtiquetas.contains("Olavarría"));
    assertTrue(nombresEtiquetas.contains("Grave"));
    assertEquals(2, nombresEtiquetas.size());
  }

  // Utilidades
  private Hecho crearHecho(String titulo, Categoria categoria, LocalDateTime fecha) {
    return new Hecho(
        titulo,
        "Descripción de prueba",
        categoria,
        new Ubicacion(0.0, 0.0),
        fecha,
        LocalDateTime.now(),
        new Origen("Fuente test"),
        new ArrayList<>(),
        true
    );
  }
}