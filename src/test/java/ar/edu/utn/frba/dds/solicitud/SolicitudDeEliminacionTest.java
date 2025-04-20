package ar.edu.utn.frba.dds.solicitud;

import ar.edu.utn.frba.dds.contenido.*;
import ar.edu.utn.frba.dds.criterio.Categoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class SolicitudDeEliminacionTest {

  private Hecho hecho;

  @BeforeEach
  public void setUp() {
    Categoria categoria = new Categoria("Evento sanitario");
    Ubicacion ubicacion = new Ubicacion("San Lorenzo, Santa Fe", -32.786098, -60.741543);
    LocalDateTime fechaHecho = LocalDateTime.of(2005, 7, 5, 0, 0);

    hecho = new Hecho(
        "Brote de enfermedad contagiosa causa estragos en San Lorenzo, Santa Fe",
        "Grave brote de enfermedad contagiosa ocurrió en las inmediaciones de San Lorenzo, Santa Fe. El incidente dejó varios heridos y daños materiales. Se ha declarado estado de emergencia en la región para facilitar la asistencia.",
        categoria,
        ubicacion,
        fechaHecho,
        LocalDateTime.now(),
        Origen.ARCHIVO,
        Collections.emptyList(),
        true
    );
  }

  @Test
  public void testRechazarSolicitud() {
    String motivo = "El hecho es relevante y fue verificado por múltiples fuentes confiables. " +
        "Además, ha sido utilizado en varias investigaciones académicas, por lo tanto no debería eliminarse del sistema. " +
        "Este incidente ha tenido gran impacto en la región y es importante preservarlo como información histórica relevante.";
    SolicitudDeEliminacion solicitud = new SolicitudDeEliminacion(hecho, motivo);

    // Simulamos que pasó 1 día (opcional si lo querés usar para lógica futura)
    solicitud.rechazarSolicitud();

    assertEquals(EstadoDeSolicitud.RECHAZADA, solicitud.getEstadoDeSolicitud());
    assertTrue(hecho.isVisible());
    assertFalse(HechosEliminados.getHechosEliminados().contains(hecho));
  }

  @Test
  public void testAceptarSolicitud() {
    String motivo = "Este hecho contiene información personal sensible que debe ser removida por respeto a la privacidad de los involucrados. " +
        "Además, se ha solicitado su eliminación por autoridades locales, y no es relevante para los fines de la aplicación.";
    SolicitudDeEliminacion solicitud = new SolicitudDeEliminacion(hecho, motivo);

    solicitud.aceptarSolicitud();

    assertEquals(EstadoDeSolicitud.ACEPTADA, solicitud.getEstadoDeSolicitud());
    assertFalse(hecho.isVisible());
    assertTrue(HechosEliminados.getHechosEliminados().contains(hecho));
  }
}