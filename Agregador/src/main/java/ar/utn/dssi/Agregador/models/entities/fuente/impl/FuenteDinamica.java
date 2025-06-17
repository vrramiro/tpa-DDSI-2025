package ar.utn.dssi.Agregador.models.entities.fuente.impl;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.HechoInputDTO;
import ar.utn.dssi.Agregador.models.entities.content.Origen;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;

public class FuenteDinamica {
  private Long idFuente;
  private Origen tipoFuente;
  private WebClient webClient;

  public FuenteDinamica() {
  }

  public List<HechoInputDTO> obtenerNuevosHechos() {
    return webClient
        .get()
        .uri(uriBuilder -> uriBuilder
            .path("/hechos/nuevos")
            .build())
        .retrieve()
        .bodyToMono(List.class) //TODO
        .block();
  }
  
  public List<HechoInputDTO> obtenerHechos() {
    return webClient
        .get()
        .uri(uriBuilder -> uriBuilder
            .path("/hechos")
            .build())
        .retrieve()
        .bodyToMono(List.class) //TODO
        .block();
  }
}
