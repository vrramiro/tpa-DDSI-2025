package ar.utn.dssi.FuenteProxy.models.entities.normalizador.impl;


import ar.utn.dssi.FuenteProxy.models.DTOs.input.HechoInputDTONormalizador;
import ar.utn.dssi.FuenteProxy.models.DTOs.output.HechoOutputDTONormalizador;
import ar.utn.dssi.FuenteProxy.models.entities.Hecho;
import ar.utn.dssi.FuenteProxy.models.mappers.MapperDeHechos;
import ar.utn.dssi.FuenteProxy.models.entities.normalizador.INormalizadorAdapter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class NormalizadorAdapter implements INormalizadorAdapter {
    private final WebClient webClient;
    private final Integer timeoutMs;

    public NormalizadorAdapter(@Value("${normalizador.base-url}") String baseUrl, @Value("${timeout-ms}") Integer timeoutMs) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
        this.timeoutMs = timeoutMs;
    }

    @Override
    public Mono<Hecho> obtenerHechoNormalizado(Hecho hecho) {
        HechoOutputDTONormalizador hechoDTO = MapperDeHechos.hechoToOutputNormalizador(hecho);

        return webClient.post()
                .uri("hecho/normalizar") // la misma URL, sin query params
                .bodyValue(hechoDTO)          // aquí envías el objeto como body
                .retrieve()
                .bodyToMono(HechoInputDTONormalizador.class) // respuesta esperada
                .timeout(Duration.ofMillis(timeoutMs))
                .map(MapperDeHechos::hechoFromInputDTONormalizador);
    }
}
