package ar.utn.dssi.Estadisticas.models.entities.calculadores.impl;

import ar.utn.dssi.Estadisticas.models.entities.Estadistica;
import ar.utn.dssi.Estadisticas.models.entities.SolicitudDeEliminacion;
import ar.utn.dssi.Estadisticas.models.entities.TipoEstadistica;
import ar.utn.dssi.Estadisticas.models.entities.calculadores.ICalculadorDeEstadisticas;
import ar.utn.dssi.Estadisticas.models.entities.data.ContextoDeCalculo;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CantidadSolicitudesEliminacionSpam implements ICalculadorDeEstadisticas {

  @Override
  public List<Estadistica> generarEstadistica(ContextoDeCalculo datos) {
    List<SolicitudDeEliminacion> solicitudes = datos.getSolicitudDeEliminacion();
    List<Estadistica> estadisticas = new ArrayList<>();

    Long spamPorSolicitudes = solicitudes.stream().filter(solicitud -> Boolean.TRUE.equals(solicitud.getEsSpam())).count();

    Estadistica estadistica = Estadistica.builder()
        .tipo(TipoEstadistica.SOLICITUD_SPAM)
        .valor(spamPorSolicitudes)
        .clave("Spam")
        .fechaDeCalculo(LocalDateTime.now())
        .build();

    estadisticas.add(estadistica);

    return estadisticas;
  }
}
