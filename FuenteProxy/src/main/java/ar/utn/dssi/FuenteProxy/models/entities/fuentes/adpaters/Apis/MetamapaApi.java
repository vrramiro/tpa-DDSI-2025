package ar.utn.dssi.FuenteProxy.models.entities.fuentes.adpaters.Apis;

import ar.utn.dssi.FuenteProxy.models.DTOs.external.MetaMapa.HechosMetaMapa;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class MetamapaApi {

    private final WebClient webClient;

    public MetamapaApi(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://88019b83-d71c-4909-a36a-fbeb7145813c.mock.pstmn.io")
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