package ar.utn.dssi.FuenteDinamica.services.impl;

import ar.utn.dssi.FuenteDinamica.models.DTOs.inputs.UbicacionInputDTO;
import ar.utn.dssi.FuenteDinamica.models.DTOs.outputs.UbicacionOutputDTO;
import ar.utn.dssi.FuenteDinamica.models.entities.Ubicacion;
import ar.utn.dssi.FuenteDinamica.services.IUbicacionService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class UbicacionService implements IUbicacionService {
    private final WebClient webClient;
    @Value("${georef.timeout-ms}")
    private int timeoutMs;

    public UbicacionService( @Value("${georef.base-url}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<Ubicacion> obtenerUbicacionDeAPI(Double latitud, Double longitud) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/ubicacion")
                        .queryParam("lat", latitud)
                        .queryParam("lon", longitud)
                        .build())
                .retrieve()
                .bodyToMono(UbicacionInputDTO.class)
                .timeout(Duration.ofMillis(timeoutMs)) // aplica el timeout definido (LO USO PORQUE EN HECHOS PUSE UN BLOCK)
                .map(resp -> {
                    Ubicacion ubicacion = new Ubicacion();
                    ubicacion.setLatitud(resp.getUbicacion().getLat());
                    ubicacion.setLongitud(resp.getUbicacion().getLon());
                    ubicacion.setPais("Argentina");
                    ubicacion.setProvincia(resp.getUbicacion().getProvincia().getNombre());
                    ubicacion.setCiudad(resp.getUbicacion().getDepartamento().getNombre());
                    return ubicacion;
                });
    }
}
