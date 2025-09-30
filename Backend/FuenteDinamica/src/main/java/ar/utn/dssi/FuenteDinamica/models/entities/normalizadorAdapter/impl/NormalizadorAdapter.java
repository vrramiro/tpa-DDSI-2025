package ar.utn.dssi.FuenteDinamica.models.entities.normalizadorAdapter.impl;

import ar.utn.dssi.FuenteDinamica.models.DTOs.inputs.HechoInputDTONormalizador;
import ar.utn.dssi.FuenteDinamica.models.DTOs.outputs.HechoOutputDTONormalizador;
import ar.utn.dssi.FuenteDinamica.models.entities.Hecho;
import ar.utn.dssi.FuenteDinamica.models.entities.normalizadorAdapter.INormalizadorAdapter;
import ar.utn.dssi.FuenteDinamica.models.mappers.MapperDeHechos;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class NormalizadorAdapter implements INormalizadorAdapter {
    private final WebClient webClient;

    private final Integer timeoutMs;

    public NormalizadorAdapter(@Value("${normalizador.base-url}") String baseUrl, @Value("${timeout-ms}") Integer timeoutMs) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
        this.timeoutMs = timeoutMs;
    }

    @Override
    public Mono<Hecho> obtenerHechoNormalizado(HechoOutputDTONormalizador hechoDTO) {
        return webClient.post()
                .uri("/hecho/normalizar") // la misma URL, sin query params
                .bodyValue(hechoDTO)          // aquí envías el objeto como body
                .retrieve()
                .bodyToMono(HechoInputDTONormalizador.class) // respuesta esperada
                .timeout(Duration.ofMillis(timeoutMs))
                .map(MapperDeHechos::hechoFromInputDTONormalizador);
    }
}
