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
    private final String agregadorServiceUrl;
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

    public void eliminarColeccion(String handle) {
        String url = UriComponentsBuilder
                .fromUriString(agregadorServiceUrl)
                .path("/admin/colecciones/{handle}")
                .buildAndExpand(handle)
                .toUriString();

        try {
            webApiCallerService.delete(url);
        } catch (WebClientResponseException e) {
            log.error("Error al eliminar colección: {}", e.getResponseBodyAsString());
            throw new ServicioNormalizadorException("Error al eliminar la colección.", e);
        } catch (Exception e) {
            log.error("Error inesperado al eliminar colección", e);
            throw new RuntimeException("Error inesperado al eliminar la colección.", e);
        }
    }

    public ColeccionResponseDTO crearColeccion(ColeccionRequestDTO coleccion) {
        String url = UriComponentsBuilder
                .fromUriString(agregadorServiceUrl)
                .path("/admin/colecciones")
                .toUriString();

        try {
            Map<String, Object> payload = construirPayload(coleccion);
            return webApiCallerService.post(url, payload, ColeccionResponseDTO.class);
        } catch (WebClientResponseException e) {
            String mensajeError = e.getResponseBodyAsString();
            throw new ServicioNormalizadorException(mensajeError, e);
        } catch (Exception e) {
            log.error("Error creando colección", e);
            throw new RuntimeException("Error inesperado al crear colección", e);
        }
    }

    public ColeccionResponseDTO actualizarColeccion(String handle, ColeccionRequestDTO coleccion) {
        String url = UriComponentsBuilder
                .fromUriString(agregadorServiceUrl)
                .path("/admin/colecciones/{handle}")
                .buildAndExpand(handle)
                .toUriString();

        try {
            Map<String, Object> payload = construirPayload(coleccion);
            return webApiCallerService.put(url, payload, ColeccionResponseDTO.class);
        } catch (WebClientResponseException e) {
            String mensajeBackend = e.getResponseBodyAsString();
            throw new ServicioNormalizadorException("Error al actualizar: " + mensajeBackend, e);
        } catch (Exception e) {
            log.error("Error inesperado al actualizar colección", e);
            throw new RuntimeException("Error inesperado al actualizar colección", e);
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
                    .bodyToMono(new ParameterizedTypeReference<PageResponseDTO<ColeccionResponseDTO>>() {})
                    .onErrorResume(WebClientResponseException.class, e -> {
                        log.error("Error {} al llamar /public/colecciones: {}", e.getStatusCode(), e.getMessage());
                        return Mono.just(new PageResponseDTO<>());
                    })
                    .defaultIfEmpty(new PageResponseDTO<>())
                    .block();
        } catch (Exception e) {
            log.error("Error de conexión al obtener colecciones: {}", e.getMessage());
            return new PageResponseDTO<>();
        }
    }

    private Map<String, Object> construirPayload(ColeccionRequestDTO coleccion) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("titulo", coleccion.getTitulo());
        payload.put("descripcion", coleccion.getDescripcion());

        if (coleccion.getConsenso() != null && coleccion.getConsenso().getTipo() != null) {
            payload.put("consenso", coleccion.getConsenso().getTipo().name());
        }

        List<Map<String, String>> listaCriterios = getMaps(coleccion);
        payload.put("criteriosDePertenecias", listaCriterios);

        List<Map<String, String>> listaFuentes = new ArrayList<>();
        if (coleccion.getFuentes() != null) {
            for (TipoFuente tf : coleccion.getFuentes()) {
                if (tf != null) {
                    listaFuentes.add(Map.of("tipoFuente", tf.name()));
                }
            }
        }
        payload.put("fuentes", listaFuentes);

        return payload;
    }

    private static List<Map<String, String>> getMaps(ColeccionRequestDTO coleccion) {
        List<Map<String, String>> listaCriterios = new ArrayList<>();
        if (coleccion.getCriterios() != null) {
            for (CriterioDTO criterio : coleccion.getCriterios()) {
                if (criterio.getTipo() != null && criterio.getValor() != null && !criterio.getValor().isBlank()) {
                    Map<String, String> cMap = new HashMap<>();
                    cMap.put("tipo_criterio", criterio.getTipo().name());
                    cMap.put("valor", criterio.getValor());
                    listaCriterios.add(cMap);
                }
            }
        }
        if (coleccion.getCategoria() != null && coleccion.getCategoria().getCategoria() != null
                && !coleccion.getCategoria().getCategoria().isBlank()) {
            Map<String, String> catMap = new HashMap<>();
            catMap.put("tipo_criterio", "CATEGORIA");
            catMap.put("valor", coleccion.getCategoria().getCategoria());
            listaCriterios.add(catMap);
        }
        return listaCriterios;
    }

    public List<ColeccionResponseDTO> obtenerTodasLasColecciones() {
        String url = UriComponentsBuilder
                .fromUriString(agregadorServiceUrl)
                .path("/public/colecciones/todas")
                .toUriString();
        try {
            return webApiCallerService.getListPublic(url, ColeccionResponseDTO.class);
        } catch (Exception e) {
            log.error("Error al obtener todas las colecciones", e);
            throw new RuntimeException("No se pudieron cargar todas las colecciones", e);
        }
    }
}