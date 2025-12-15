package ar.utn.dssi.Estadisticas.models.entities.adapters.agregador.impl;

import ar.utn.dssi.Estadisticas.dto.input.ColeccionInputDTO;
import ar.utn.dssi.Estadisticas.dto.input.HechoInputDTO;
import ar.utn.dssi.Estadisticas.mappers.MapperDeColecciones;
import ar.utn.dssi.Estadisticas.mappers.MapperDeHechos;
import ar.utn.dssi.Estadisticas.models.entities.adapters.agregador.IAgregadorAdapter;
import ar.utn.dssi.Estadisticas.models.entities.data.Coleccion;
import ar.utn.dssi.Estadisticas.models.entities.data.Hecho;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;

@Component
public class AgregadorAdapter implements IAgregadorAdapter {
  private final WebClient webClient;

  public AgregadorAdapter(@Value("${agregador.base-url}") String baseUrl) {
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
        .uri("/public/colecciones/todas")
        .retrieve()
        .bodyToFlux(ColeccionInputDTO.class)
        .map(MapperDeColecciones::coleccionFromInputDTO)
        .collectList()
        .block();
  }

  @Override
  public Long obtenerSolicitudesSpam() {
    return webClient.get()
        .uri("/public/solicitudes-eliminacion/spam-cantidad")
        .retrieve()
        .bodyToMono(Long.class)
        .block();
  }
}
