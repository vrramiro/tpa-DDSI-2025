package ar.utn.dssi.Agregador.models.entities.normalizadorAdapter.impl;

import ar.utn.dssi.Agregador.dto.input.CategoriaNormalizadorDTO;
import ar.utn.dssi.Agregador.models.entities.Categoria;
import ar.utn.dssi.Agregador.models.entities.normalizadorAdapter.INormalizadorAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.time.Duration;
import java.util.Optional;

@Component
public class NormalizadorAdapter implements INormalizadorAdapter {

    private final WebClient webClient;
    private final Integer timeoutMs;

    public NormalizadorAdapter(@Value("${normalizador.base-url}") String baseUrl,
                               @Value("${timeout-ms}") Integer timeoutMs) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
        this.timeoutMs = timeoutMs;
    }

    @Override
    public Optional<Categoria> obtenerCategoriaPorId(Long idCategoria) {
        try {
            CategoriaNormalizadorDTO dto = webClient.get()
                    .uri("/categoria/{id}", idCategoria)
                    .retrieve()
                    .bodyToMono(CategoriaNormalizadorDTO.class)
                    .timeout(Duration.ofMillis(timeoutMs))
                    .block();

            if (dto != null) {
                Categoria categoria = new Categoria();
                categoria.setId(dto.getId());
                categoria.setNombre(dto.getNombre());
                return Optional.of(categoria);
            }
        } catch (Exception e) {
            System.err.println("Error al obtener categoría del normalizador: " + e.getMessage());
        }
        return Optional.empty();
    }
}