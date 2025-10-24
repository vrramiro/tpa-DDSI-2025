package ar.utn.dssi.app_web.services.impl;

import ar.utn.dssi.app_web.dto.CategoriaDTO;
import ar.utn.dssi.app_web.services.Interfaces.ICategoriaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoriaService implements ICategoriaService {

    private static final Logger log = LoggerFactory.getLogger(CategoriaService.class);
    private final WebClient webClient;
    private final String apiBaseUrl;

    public CategoriaService(@Value("${normalizador.service.url}") String apiBaseUrl) {
        this.webClient = WebClient.builder().build();
        this.apiBaseUrl = apiBaseUrl;
    }

    public List<CategoriaDTO> obtenerCategorias() {
        String url = UriComponentsBuilder
                .fromUriString(apiBaseUrl)
                .path("/categoria/categorias")
                .toUriString();

        try {
            List<CategoriaDTO> categorias = webClient
                    .get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<CategoriaDTO>>() {})
                    .block();

            return categorias != null ? categorias : new ArrayList<>();
        } catch (Exception e) {
            log.error("Error al obtener categorías: {}", e.getMessage());
            return new ArrayList<>();
        }
    }
}