package ar.utn.dssi.FuenteProxy.models.entities.fuentes.adpaters.Apis;

import ar.utn.dssi.FuenteProxy.models.DTOs.external.MetaMapa.HechosMetaMapa;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class MetamapaApi {
    private final WebClient webClient;

    public MetamapaApi(String baseUrl) {
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