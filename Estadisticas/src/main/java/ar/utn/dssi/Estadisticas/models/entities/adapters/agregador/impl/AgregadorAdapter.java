package ar.utn.dssi.Estadisticas.models.entities.adapters.agregador.impl;

import ar.utn.dssi.Estadisticas.dto.input.ColeccionInputDTO;
import ar.utn.dssi.Estadisticas.dto.input.HechoInputDTO;
import ar.utn.dssi.Estadisticas.mappers.MapperDeColecciones;
import ar.utn.dssi.Estadisticas.mappers.MapperDeHechos;
import ar.utn.dssi.Estadisticas.models.entities.adapters.agregador.IAgregadorAdapter;
import ar.utn.dssi.Estadisticas.models.entities.data.Coleccion;
import ar.utn.dssi.Estadisticas.models.entities.data.Hecho;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Slf4j
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
    List<Coleccion> colecciones = webClient.get()
        .uri("/public/colecciones/todas")
        .retrieve()
        .bodyToFlux(ColeccionInputDTO.class)
        .map(MapperDeColecciones::coleccionFromInputDTO)
        .collectList()
        .block();

    if (colecciones != null) {
      for (Coleccion coleccion : colecciones) {
        List<Hecho> hechosDeColeccion = this.obtenerHechosDeColeccion(coleccion.getHandle());
        coleccion.setHechos(hechosDeColeccion);
      }
    }

    return colecciones;
  }

  @Override
  public Long obtenerSolicitudesSpam() {
    return webClient.get()
        .uri("/public/solicitudes-eliminacion/spam-cantidad")
        .retrieve()
        .bodyToMono(Long.class)
        .block();
  }

  private List<Hecho> obtenerHechosDeColeccion(String handle) {
    try {
      HechosPageDTO response = webClient.get()
              .uri(uriBuilder -> uriBuilder
                      .path("/public/colecciones/{handle}/hechos")
                      .queryParam("size", 10000)
                      .build(handle))
              .retrieve()
              .bodyToMono(HechosPageDTO.class)
              .block();

      if (response != null && response.getContent() != null) {
        return response.getContent().stream()
                .map(MapperDeHechos::hechoFromInput)
                .toList();
      }
    } catch (Exception e) {
      log.error("Error al obtener hechos para la colección {}: {}", handle, e.getMessage());
    }
    return new ArrayList<>();
  }

  @Data
  private static class HechosPageDTO {
    private List<HechoInputDTO> content;
  }
}
