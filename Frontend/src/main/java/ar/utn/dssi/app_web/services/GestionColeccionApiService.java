package ar.utn.dssi.app_web.services;

import ar.utn.dssi.app_web.dto.Criterio.CriterioDTO;
import ar.utn.dssi.app_web.dto.Fuente.TipoFuente;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            Map<String, Object> payload = new HashMap<>();
            payload.put("titulo", coleccion.getTitulo());
            payload.put("descripcion", coleccion.getDescripcion());

            if (coleccion.getConsenso() != null && coleccion.getConsenso().getTipo() != null) {
                payload.put("consenso", coleccion.getConsenso().getTipo().name());
            }

            List<Map<String, String>> listaCriteriosParaEnviar = new ArrayList<>();

            if (coleccion.getCriterios() != null) {
                for (CriterioDTO criterio : coleccion.getCriterios()) {
                    if (criterio.getValor() != null && !criterio.getValor().isBlank()) {
                        listaCriteriosParaEnviar.add(Map.of(
                                "tipo_criterio", criterio.getTipo().name(),
                                "valor", criterio.getValor()
                        ));
                    }
                }
            }

            if (coleccion.getCategoria() != null && coleccion.getCategoria().getCategoria() != null) {
                listaCriteriosParaEnviar.add(Map.of(
                        "tipo_criterio", "CATEGORIA",
                        "valor", coleccion.getCategoria().getCategoria()
                ));
            }

            payload.put("criteriosDePertenecias", listaCriteriosParaEnviar);

            List<Map<String, String>> listaFuentes = new ArrayList<>();
            if (coleccion.getFuentes() != null) {
                for (TipoFuente tf : coleccion.getFuentes()) {
                    listaFuentes.add(Map.of("tipoFuente", tf.name()));
                }
            }
            payload.put("fuentes", listaFuentes);

            return webApiCallerService.post(url, payload, ColeccionResponseDTO.class);

        } catch (Exception e) {
            log.error("Error creando colección", e);
            throw new ServicioNormalizadorException("Error inesperado al crear colección", e);
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

