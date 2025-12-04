package ar.utn.dssi.app_web.services;

import ar.utn.dssi.app_web.dto.input.ColeccionResponseDTO;
import ar.utn.dssi.app_web.dto.input.PageResponseDTO;
import ar.utn.dssi.app_web.dto.output.ColeccionRequestDTO;
import ar.utn.dssi.app_web.error.NotFoundException;
import ar.utn.dssi.app_web.error.ServicioNormalizadorException;
import ar.utn.dssi.app_web.services.internal.WebApiCallerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Service
public class GestionColeccionApiService {

    private static final Logger log = LoggerFactory.getLogger(GestionColeccionApiService.class);

    private final WebApiCallerService webApiCallerService;
    private final String agregadorServiceUrl; // URL del servicio de agregador
    private final WebClient webClient;

    public GestionColeccionApiService(WebApiCallerService webApiCallerService,
                                      @Value("${agregador.service.url}") String agregadorServiceUrl,
                                      WebClient.Builder builder) {
        this.webApiCallerService = webApiCallerService;
        this.agregadorServiceUrl = agregadorServiceUrl;
        this.webClient = builder
                .baseUrl(agregadorServiceUrl)
                .build();
    }

    public ColeccionResponseDTO obtenerColeccion(String handle) {
        String url = UriComponentsBuilder
                .fromUriString(agregadorServiceUrl)
                .path("/public/colecciones/" + handle)
                .toUriString();

        try {
            return webApiCallerService.getPublic(url, ColeccionResponseDTO.class);
        } catch (Exception e) {
            log.error("Error al obtener la colección con handle {}: {}", handle, e.getMessage());
            throw new NotFoundException("Colección", handle);
        }
    }

    public void eliminarColeccion(Long id) {
        String url = UriComponentsBuilder
                .fromUriString(agregadorServiceUrl)
                .path("/admin/colecciones")
                .queryParam("id", id)
                .toUriString();
        try {
            webApiCallerService.delete(url);
        } catch (WebClientException e) {
            throw new ServicioNormalizadorException("Error de conexión con servicio normalizador", e);
        } catch (Exception e) {
            log.error("Error inesperado al obtener ubicación", e);
            throw new ServicioNormalizadorException("Error inesperado al normalizar ubicación", e);
        }
    }

    public ColeccionResponseDTO crearColeccion(ColeccionRequestDTO coleccion) {
        String url = UriComponentsBuilder
                .fromUriString(agregadorServiceUrl)
                .path("/admin/colecciones")
                .toUriString();
        try {
            return webApiCallerService.post(url, coleccion, ColeccionResponseDTO.class);
        } catch (WebClientException e) {
            throw new ServicioNormalizadorException("Error de conexión con servicio normalizador", e);
        } catch (Exception e) {
            log.error("Error inesperado al obtener ubicación", e);
            throw new ServicioNormalizadorException("Error inesperado al normalizar ubicación", e);
        }
    }

    public ColeccionResponseDTO actualizarColeccion(Long id) {
        String url = UriComponentsBuilder
                .fromUriString(agregadorServiceUrl)
                .path("/admin/colecciones")
                .queryParam("id", id)
                .toUriString();
        try {
            return webApiCallerService.put(url, null, ColeccionResponseDTO.class);
        } catch (WebClientException e) {
            throw new ServicioNormalizadorException("Error de conexión con servicio normalizador", e);
        } catch (Exception e) {
            log.error("Error inesperado al obtener ubicación", e);
            throw new ServicioNormalizadorException("Error inesperado al normalizar ubicación", e);
        }
    }

    public PageResponseDTO<ColeccionResponseDTO> obtenerColecciones(Integer page, Integer size) {
        try {
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/public/colecciones")
                            .queryParam("page", page)
                            .queryParam("size", size)
                            .build()
                    )
                    .retrieve()
                    .onStatus(status -> status.value() == 204,
                            clientResponse -> clientResponse.bodyToMono(Void.class).thenReturn(null))
                    .bodyToMono(new ParameterizedTypeReference<PageResponseDTO<ColeccionResponseDTO>>() {})
                    .onErrorResume(WebClientResponseException.class, e -> {
                        log.error("Error {} al llamar /public/colecciones: {}", e.getStatusCode(), e.getMessage());
                        return Mono.just(new PageResponseDTO<ColeccionResponseDTO>());
                    })
                    .defaultIfEmpty(new PageResponseDTO<ColeccionResponseDTO>())
                    .block();
        } catch (Exception e) {
            log.error("Error de conexión al obtener colecciones: {}", e.getMessage());
            return new PageResponseDTO<ColeccionResponseDTO>();
        }
    }
}

