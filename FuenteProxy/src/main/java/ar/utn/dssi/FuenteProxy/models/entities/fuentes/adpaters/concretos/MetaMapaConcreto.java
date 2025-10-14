package ar.utn.dssi.FuenteProxy.models.entities.fuentes.adpaters.concretos;

import ar.utn.dssi.FuenteProxy.dto.external.MetaMapa.HechosMetaMapa;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class MetaMapaConcreto {
  private final WebClient webClient;

  public MetaMapaConcreto(String baseUrl) {
    this.webClient = WebClient.builder()
        .baseUrl(baseUrl)
        .build();
  }

  public Mono<HechosMetaMapa> obtenerHechos() {
    return webClient.get()
        .uri(uriBuilder -> uriBuilder
            .path("/api/hechos")
            .build())
        .retrieve()
        .bodyToMono(HechosMetaMapa.class);
  }
}