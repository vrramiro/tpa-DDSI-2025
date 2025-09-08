package ar.utb.ba.dsi.estadisticas.models.entities.calculadores.impl;

import ar.utb.ba.dsi.estadisticas.models.DTOs.inputDTOs.ColeccionInputDTO;
import ar.utb.ba.dsi.estadisticas.models.DTOs.inputDTOs.HechoInputDTO;
import ar.utb.ba.dsi.estadisticas.models.DTOs.inputDTOs.SolicitudDeEliminacionInputDTO;
import ar.utb.ba.dsi.estadisticas.models.DTOs.inputDTOs.UbicacionInputDTO;
import ar.utb.ba.dsi.estadisticas.models.entities.DatosDeCalculo;
import ar.utb.ba.dsi.estadisticas.models.entities.Estadistica;
import ar.utb.ba.dsi.estadisticas.models.entities.TipoEstadistica;
import ar.utb.ba.dsi.estadisticas.models.entities.calculadores.IGeneradorDeEstadisticas;
import ar.utb.ba.dsi.estadisticas.models.entities.SolicitudDeEliminacion;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CantidadSolicitudesEliminacionSpam implements IGeneradorDeEstadisticas {

  @Override
  public List<Estadistica> generarEstadistica(DatosDeCalculo datos) {
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
