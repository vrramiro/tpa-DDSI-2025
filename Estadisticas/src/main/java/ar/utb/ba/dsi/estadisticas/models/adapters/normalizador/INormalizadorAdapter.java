package ar.utb.ba.dsi.estadisticas.models.adapters.normalizador;

import ar.utb.ba.dsi.estadisticas.models.entities.data.Categoria;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface INormalizadorAdapter {
  public List<Categoria> obtenerCategorias(); //lo dejo como input revisar entidad de dominio
}
