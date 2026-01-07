package ar.utn.dssi.FuenteDinamica.models.entities.normalizadorAdapter.impl;

import ar.utn.dssi.FuenteDinamica.dto.input.CategoriaNormalizadorDTO;
import ar.utn.dssi.FuenteDinamica.dto.input.HechoInputDTONormalizador;
import ar.utn.dssi.FuenteDinamica.dto.input.UbicacionInputDTO;
import ar.utn.dssi.FuenteDinamica.dto.output.HechoOutputDTONormalizador;
import ar.utn.dssi.FuenteDinamica.mappers.MapperDeCategoria;
import ar.utn.dssi.FuenteDinamica.mappers.MapperDeHechos;
import ar.utn.dssi.FuenteDinamica.mappers.MapperDeUbicacion;
import ar.utn.dssi.FuenteDinamica.models.entities.Categoria;
import ar.utn.dssi.FuenteDinamica.models.entities.Hecho;
import ar.utn.dssi.FuenteDinamica.models.entities.Ubicacion;
import ar.utn.dssi.FuenteDinamica.models.entities.normalizadorAdapter.INormalizadorAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.net.UnknownHostException;
import java.time.Duration;

@Component
public class NormalizadorAdapter implements INormalizadorAdapter {
  private final WebClient webClient;
  private final Integer timeoutMs;

  public NormalizadorAdapter(@Value("${normalizador.base-url}") String baseUrl, 
                             @Value("${timeout-ms}") Integer timeoutMs) {
    this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    this.timeoutMs = timeoutMs;
  }

  @Override
  public Mono<Hecho> obtenerHechoNormalizado(Hecho hecho) {
    HechoOutputDTONormalizador output = MapperDeHechos.hechoOutputNormalizadorFromHecho(hecho);

    return webClient.post()
        .uri("/hecho/normalizar")
        .bodyValue(output)
        .retrieve()
        .bodyToMono(HechoInputDTONormalizador.class)
        .timeout(Duration.ofMillis(timeoutMs))
        .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2))
            .filter(this::isNetworkError))
        .flatMap(dto -> {
          if (dto == null) {
            return Mono.error(new RuntimeException("El servicio normalizador devolvió null"));
          }
          Hecho normalizado = MapperDeHechos.hechoFromInputDTONormalizador(dto);
          if (normalizado == null) {
            return Mono.error(new RuntimeException("El mapper devolvió null"));
          }
          return Mono.just(normalizado);
        });
  }

  @Override
  public Mono<Categoria> obtenerCategoria(Long idCategoria) {
    return webClient.get()
        .uri(uriBuilder -> uriBuilder
            .path("/categoria/{idCategoria}")
            .build(idCategoria)
        )
        .retrieve()
        .bodyToMono(CategoriaNormalizadorDTO.class)
        .timeout(Duration.ofMillis(timeoutMs))
        .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2))
            .filter(this::isNetworkError))
        .flatMap(categoriaNormalizador -> {
          if (categoriaNormalizador == null) {
            return Mono.error(new RuntimeException("El servicio normalizador devolvió null"));
          }
          Categoria categoria = MapperDeCategoria.categoriaFromCategoriaNormalizadorDTO(categoriaNormalizador);
          if (categoria == null) {
            return Mono.error(new RuntimeException("El mapper devolvió null"));
          }
          return Mono.just(categoria);
        });
  }

  @Override
  public Mono<Ubicacion> obtenerUbicacionNormalizada(Double latitud, Double longitud) {
    return webClient.get()
        .uri(uriBuilder -> uriBuilder
            .path("/ubicacion/normalizar")
            .queryParam("latitud", latitud)
            .queryParam("longitud", longitud)
            .build()
        )
        .retrieve()
        .bodyToMono(UbicacionInputDTO.class)
        .timeout(Duration.ofMillis(timeoutMs))
        .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2))
            .filter(this::isNetworkError))
        .flatMap(dto -> {
          if (dto == null) {
            return Mono.error(new RuntimeException("El servicio normalizador devolvió null"));
          }
          Ubicacion normalizada = MapperDeUbicacion.ubicacionFromInput(dto);
          if (normalizada == null) {
            return Mono.error(new RuntimeException("El mapper devolvió null"));
          }
          return Mono.just(normalizada);
        });
  }

  /**
   * Determina si el error es de red o resolución DNS para aplicar reintentos.
   */
  private boolean isNetworkError(Throwable throwable) {
    return throwable instanceof WebClientRequestException || 
           throwable.getCause() instanceof UnknownHostException;
  }
}
