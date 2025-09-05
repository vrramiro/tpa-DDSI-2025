package ar.utb.ba.dsi.Normalizador.models.entities.impl;

import ar.utb.ba.dsi.Normalizador.models.DTOs.Output.UbicacionOutputDTO;
import ar.utb.ba.dsi.Normalizador.models.DTOs.Input.UbicacionInputDTOGeoref;
import ar.utb.ba.dsi.Normalizador.models.entities.IUbicacionAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class GeorefAdapter implements IUbicacionAdapter {
    private final WebClient webClient;

    @Value("${georef.timeout-ms}")
    private int timeoutMs;

    public GeorefAdapter( @Value("${georef.base-url}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<UbicacionOutputDTO> obtenerUbicacionDeAPI(Double latitud, Double longitud) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/ubicacion")
                        .queryParam("lat", latitud)
                        .queryParam("lon", longitud)
                        .build())
                .retrieve()
                .bodyToMono(UbicacionInputDTOGeoref.class)
                .timeout(Duration.ofMillis(timeoutMs)) // aplica el timeout definido (LO USO PORQUE EN HECHOS PUSE UN BLOCK)
                .map(resp -> {
                    UbicacionOutputDTO ubicacion = new UbicacionOutputDTO();
                    ubicacion.setLatitud(resp.getUbicacion().getLat());
                    ubicacion.setLongitud(resp.getUbicacion().getLon());
                    ubicacion.setPais("Argentina");
                    ubicacion.setProvincia(resp.getUbicacion().getProvincia().getNombre());
                    ubicacion.setCiudad(resp.getUbicacion().getDepartamento().getNombre());
                    return ubicacion;
                });
    }
}
