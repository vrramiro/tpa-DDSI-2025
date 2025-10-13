package ar.utn.dssi.Estadisticas.models.entities.adapters.normalizador;

import ar.utn.dssi.Estadisticas.models.entities.data.Categoria;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public interface INormalizadorAdapter {
  public Mono<List<Categoria>> obtenerCategorias(); //lo dejo como input revisar entidad de dominio
}
