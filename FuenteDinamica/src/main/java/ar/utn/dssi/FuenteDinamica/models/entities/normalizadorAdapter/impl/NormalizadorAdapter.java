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
    HechoOutputDTONormalizador output = MapperDeHechos.hechoOutputNormalizadorFromHecho(hecho);

    return webClient.post()
        .uri("/hecho/normalizar") // la misma URL, sin query params
        .bodyValue(output)          // aquí envías el objeto como body
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

  @Override
  public Mono<Categoria> obtenerCategoria(Long idCategoria) {
    return webClient.get()
        .uri(uriBuilder -> uriBuilder
            .path("/categoria/{idCategoria}")
            .build(idCategoria)
        )
        .retrieve()
        .bodyToMono(CategoriaNormalizadorDTO.class) // respuesta esperada
        .timeout(Duration.ofMillis(timeoutMs))
        .flatMap(categoriaNormalizador -> {
          Categoria categoria = MapperDeCategoria.categoriaFromCategoriaNormalizadorDTO(categoriaNormalizador);
          if (categoriaNormalizador == null) {
            return Mono.error(new RuntimeException("El servicio normalizador devolvió null"));
          }
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
        .bodyToMono(UbicacionInputDTO.class) // respuesta esperada
        .timeout(Duration.ofMillis(timeoutMs))
        .flatMap(dto -> {
          Ubicacion normalizada = MapperDeUbicacion.ubicacionFromInput(dto);
          if (dto == null) {
            return Mono.error(new RuntimeException("El servicio normalizador devolvió null"));
          }
          if (normalizada == null) {
            return Mono.error(new RuntimeException("El mapper devolvió null"));
          }
          return Mono.just(normalizada);
        });
  }
}
