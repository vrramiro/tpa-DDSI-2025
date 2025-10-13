package ar.utn.dssi.Estadisticas.models.entities.adapters.normalizador.impl;

import ar.utn.dssi.Estadisticas.dto.input.CategoriaInputDTO;
import ar.utn.dssi.Estadisticas.models.entities.adapters.normalizador.INormalizadorAdapter;
import ar.utn.dssi.Estadisticas.models.entities.data.Categoria;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.time.Duration;
import java.util.List;

@Component
public class NormalizadorAdapter implements INormalizadorAdapter {
  private final WebClient webClient;
  private final Integer timeoutMs;

  public NormalizadorAdapter(@Value("${normalizador.base-url}") String baseUrl, @Value("${timeout-ms}") Integer timeoutMs) {
    this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    this.timeoutMs = timeoutMs;
  }

    @Override
    public Mono<List<Categoria>> obtenerCategorias() {
        return webClient.get()
                .uri("/categoria/categorias")
                .retrieve()
                .bodyToFlux(CategoriaInputDTO.class)
                .map(this::categoriaFromInputDTO)
                .collectList()
                .timeout(Duration.ofMillis(timeoutMs));
    }


    private Categoria categoriaFromInputDTO(CategoriaInputDTO dto) {
    Categoria categoria = new Categoria();
    categoria.setId(dto.getId());
    categoria.setNombre(dto.getCategoria());
    return categoria;
  }
}

