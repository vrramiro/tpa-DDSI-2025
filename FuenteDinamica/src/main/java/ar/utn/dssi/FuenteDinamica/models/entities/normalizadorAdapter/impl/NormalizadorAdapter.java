package ar.utn.dssi.FuenteDinamica.models.entities.normalizadorAdapter.impl;

import ar.utn.dssi.FuenteDinamica.models.DTOs.inputs.HechoInputDTONormalizador;
import ar.utn.dssi.FuenteDinamica.models.DTOs.inputs.UbicacionInputDTO;
import ar.utn.dssi.FuenteDinamica.models.DTOs.outputs.HechoOutputDTONormalizador;
import ar.utn.dssi.FuenteDinamica.models.DTOs.outputs.UbicacionOutputDTONormalizador;
import ar.utn.dssi.FuenteDinamica.models.entities.Hecho;
import ar.utn.dssi.FuenteDinamica.models.entities.Ubicacion;
import ar.utn.dssi.FuenteDinamica.models.entities.normalizadorAdapter.INormalizadorAdapter;
import ar.utn.dssi.FuenteDinamica.models.mappers.MapperDeHechos;
import ar.utn.dssi.FuenteDinamica.models.mappers.MapperDeUbicacion;
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

  public Mono<Ubicacion> obtenerUbicacionNormalizada(UbicacionOutputDTONormalizador ubicacionDTO) {
    Double latitud = ubicacionDTO.getLatitud();
    Double longitud = ubicacionDTO.getLongitud();

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
