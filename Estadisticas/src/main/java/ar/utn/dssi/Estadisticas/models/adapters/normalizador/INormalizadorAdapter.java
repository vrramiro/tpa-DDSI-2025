package ar.utn.dssi.Estadisticas.models.adapters.normalizador;

import ar.utn.dssi.Estadisticas.models.entities.data.Categoria;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface INormalizadorAdapter {
  public List<Categoria> obtenerCategorias(); //lo dejo como input revisar entidad de dominio
}
