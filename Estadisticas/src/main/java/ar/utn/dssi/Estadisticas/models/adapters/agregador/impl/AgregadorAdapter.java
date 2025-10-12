package ar.utn.dssi.Estadisticas.models.adapters.agregador.impl;

import ar.utn.dssi.Estadisticas.models.DTOs.inputs.ColeccionInputDTO;
import ar.utn.dssi.Estadisticas.models.DTOs.inputs.HechoInputDTO;
import ar.utn.dssi.Estadisticas.models.DTOs.inputs.SolicitudDeEliminacionInputDTO;
import ar.utn.dssi.Estadisticas.models.adapters.agregador.IAgregadorAdapter;
import ar.utn.dssi.Estadisticas.models.entities.SolicitudDeEliminacion;
import ar.utn.dssi.Estadisticas.models.entities.data.Coleccion;
import ar.utn.dssi.Estadisticas.models.entities.data.Hecho;
import ar.utn.dssi.Estadisticas.models.mappers.MapperDeColecciones;
import ar.utn.dssi.Estadisticas.models.mappers.MapperDeHechos;
import ar.utn.dssi.Estadisticas.models.mappers.MapperDeSolicitudEliminacion;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;

@Component
public class AgregadorAdapter implements IAgregadorAdapter {
  private final WebClient webClient;

  public AgregadorAdapter(@Value("${base-url}") String baseUrl) {
    this.webClient = WebClient.builder().baseUrl(baseUrl).build();
  }


  @Override
  public List<Hecho> obtenerHechos() {
    return webClient.get()
        .uri("/hechos")
        .retrieve()
        .bodyToFlux(HechoInputDTO.class)
        .map(MapperDeHechos::hechoFromInput)
        .collectList()
        .block();
  }

  @Override
  public List<Coleccion> obtenerColecciones() {
    return webClient.get()
        .uri("/admin/colecciones")
        .retrieve()
        .bodyToFlux(ColeccionInputDTO.class)
        .map(MapperDeColecciones::coleccionFromInputDTO)
        .collectList()
        .block();
  }

  @Override
  public List<SolicitudDeEliminacion> obtenerSolicitudes() {
    return webClient.get()
        .uri("/admin/solicitud/spam")
        .retrieve()
        .bodyToFlux(SolicitudDeEliminacionInputDTO.class)
        .map(MapperDeSolicitudEliminacion::solicitudFromInput)
        .collectList()
        .block();
  }
}
