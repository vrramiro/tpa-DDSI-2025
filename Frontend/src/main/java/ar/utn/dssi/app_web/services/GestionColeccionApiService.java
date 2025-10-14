package ar.utn.dssi.app_web.services;

import ar.utn.dssi.app_web.dto.input.ColeccionResponseDTO;
import ar.utn.dssi.app_web.dto.input.PageResponseDTO;
import ar.utn.dssi.app_web.dto.output.ColeccionRequestDTO;
import ar.utn.dssi.app_web.error.ServicioNormalizadorException;
import ar.utn.dssi.app_web.services.internal.WebApiCallerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.util.UriComponentsBuilder;

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

    public ColeccionResponseDTO obtenerColeccion(long id) {

        String url = UriComponentsBuilder
                .fromUriString(agregadorServiceUrl)
                .path("/admin/colecciones")
                .queryParam("id", id)
                .toUriString();
        try {
            ColeccionResponseDTO response = webApiCallerService.get(url, ColeccionResponseDTO.class);
            return response;
        } catch (WebClientException e) {
            throw new ServicioNormalizadorException("Error de conexión con servicio normalizador", e);
        } catch (Exception e) {
            log.error("Error inesperado al obtener ubicación", e);
            throw new ServicioNormalizadorException("Error inesperado al normalizar ubicación", e);
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
            ColeccionResponseDTO response = webApiCallerService.post(url, coleccion, ColeccionResponseDTO.class);
            return response;
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
            ColeccionResponseDTO response = webApiCallerService.put(url, null, ColeccionResponseDTO.class);
            return response;
        } catch (WebClientException e) {
            throw new ServicioNormalizadorException("Error de conexión con servicio normalizador", e);
        } catch (Exception e) {
            log.error("Error inesperado al obtener ubicación", e);
            throw new ServicioNormalizadorException("Error inesperado al normalizar ubicación", e);
        }
    }

    public PageResponseDTO<ColeccionResponseDTO> obtenerColecciones(Integer numeroPagina) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/admin/colecciones")
                        .queryParam("page", numeroPagina)
                        .build()
                )
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<PageResponseDTO<ColeccionResponseDTO>>() {})
                .block();
    }
}

