package ar.utb.ba.dsi.Normalizador.models.entities.AdapterUbicacion.impl;

import ar.utb.ba.dsi.Normalizador.models.DTOs.Input.UbicacionInputDTOGeoref;
import ar.utb.ba.dsi.Normalizador.models.entities.AdapterUbicacion.IUbicacionAdapter;
import ar.utb.ba.dsi.Normalizador.models.entities.Ubicacion;
import ar.utb.ba.dsi.Normalizador.models.mappers.MapperDeUbicacion;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class GeorefAdapter implements IUbicacionAdapter {
    private final WebClient webClient;

    private final Integer timeoutMs;

    public GeorefAdapter( @Value("${georef.base-url}") String baseUrl, @Value("${georef.timeout-ms}") Integer timeoutMs) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
        this.timeoutMs = timeoutMs;
    }

    public Mono<Ubicacion> obtenerUbicacionDeAPI(Double latitud, Double longitud) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/ubicacion")
                        .queryParam("lat", latitud)
                        .queryParam("lon", longitud)
                        .build())
                .retrieve()
                .bodyToMono(UbicacionInputDTOGeoref.class)
                .timeout(Duration.ofMillis(timeoutMs)) // aplica el timeout definido (LO USO PORQUE EN HECHOS PUSE UN BLOCK)
                .map(MapperDeUbicacion::ubicacionFromInput);
    }
}
