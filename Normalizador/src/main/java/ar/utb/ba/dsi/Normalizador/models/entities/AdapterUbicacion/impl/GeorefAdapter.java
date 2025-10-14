package ar.utb.ba.dsi.Normalizador.models.entities.AdapterUbicacion.impl;

import ar.utb.ba.dsi.Normalizador.dto.Input.ProvinciasInputDTO;
import ar.utb.ba.dsi.Normalizador.dto.Input.UbicacionInputDTOGeoref;
import ar.utb.ba.dsi.Normalizador.mappers.MapperDeUbicacion;
import ar.utb.ba.dsi.Normalizador.models.entities.AdapterUbicacion.IUbicacionAdapter;
import ar.utb.ba.dsi.Normalizador.models.entities.Ubicacion;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.time.Duration;
import java.util.List;

@Component
public class GeorefAdapter implements IUbicacionAdapter {
  private final WebClient webClient;

  private final Integer timeoutMs;

  public GeorefAdapter(@Value("${georef.base-url}") String baseUrl, @Value("${georef.timeout-ms}") Integer timeoutMs) {
    this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    this.timeoutMs = timeoutMs;
  }

  public Mono<Ubicacion> obtenerUbicacionDeAPI(Double latitud, Double longitud) {
    String campos = String.join(",", "lat", "lon", "municipio.nombre", "provincia.nombre", "departamento.nombre");

    return webClient.get()
        .uri(uriBuilder -> uriBuilder
            .path("/ubicacion")
            .queryParam("lat", latitud)
            .queryParam("lon", longitud)
            .queryParam("aplanar", true)
            .queryParam("campos", campos)
            .queryParam("formato", "json")
            .build())
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, resp -> Mono.error(new RuntimeException("Error georef " + resp.statusCode() + ": Ubicacion no encontrada para latitud: " + latitud + " y longitud: " + longitud)))
        .onStatus(HttpStatusCode::is5xxServerError, resp -> Mono.error(new RuntimeException("Error del servidor de georef")))
        .bodyToMono(UbicacionInputDTOGeoref.class)
        .timeout(Duration.ofMillis(timeoutMs)) // aplica el timeout definido (LO USO PORQUE EN HECHOS PUSE UN BLOCK)
        .map(MapperDeUbicacion::ubicacionFromInput);
  }

  public Mono<List<String>> obtenerProvinciasDeAPI() {
    return webClient.get()
        .uri(uriBuilder -> uriBuilder
            .path("/provincias")
            .queryParam("aplanar", true)
            .queryParam("campos", "nombre")
            .queryParam("formato", "json")
            .build())
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, resp -> Mono.error(new RuntimeException("Error georef " + resp.statusCode() + ": No se pudieron obtener las provincias")))
        .onStatus(HttpStatusCode::is5xxServerError, resp -> Mono.error(new RuntimeException("Error del servidor de georef")))
        .bodyToMono(ProvinciasInputDTO.class)
        .timeout(Duration.ofMillis(timeoutMs))
        .map(response -> response.getProvincias().stream().map(provincia -> provincia.getNombre()).toList());
  }
}
