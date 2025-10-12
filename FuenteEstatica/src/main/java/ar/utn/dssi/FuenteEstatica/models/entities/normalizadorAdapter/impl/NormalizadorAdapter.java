package ar.utn.dssi.FuenteEstatica.models.entities.normalizadorAdapter.impl;


import ar.utn.dssi.FuenteEstatica.models.DTOs.input.HechoInputDTONormalizador;
import ar.utn.dssi.FuenteEstatica.models.DTOs.output.HechoOutputDTONormalizador;
import ar.utn.dssi.FuenteEstatica.models.entities.contenido.Hecho;
import ar.utn.dssi.FuenteEstatica.models.entities.normalizadorAdapter.INormalizadorAdapter;
import ar.utn.dssi.FuenteEstatica.models.mappers.MapperDeHechos;
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
        .uri("/hecho/normalizar") // la misma URL, sin query params
        .bodyValue(hechoDTO)          // aquí envías el objeto como body
        .retrieve()
        .bodyToMono(HechoInputDTONormalizador.class) // respuesta esperada
        .timeout(Duration.ofMillis(timeoutMs))
        .flatMap(dto -> {
          Hecho normalizado = MapperDeHechos.hechoFromInputDTONormalizador(dto);
          if (dto == null) {
            return Mono.error(new RuntimeException("El servicio normalizador devolvió null"));
          }
          if (normalizado == null) {
            return Mono.error(new RuntimeException("El mapper devolvió null"));
          }
          return Mono.just(normalizado);
        });
  }
}
