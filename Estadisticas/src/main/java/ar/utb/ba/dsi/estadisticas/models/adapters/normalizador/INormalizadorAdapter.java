package ar.utb.ba.dsi.estadisticas.models.adapters.normalizador;

import ar.utb.ba.dsi.estadisticas.models.DTOs.inputDTOs.CategoriaInputDTO;
import java.util.List;

public interface INormalizadorAdapter {
  public List<CategoriaInputDTO> obtenerCategorias(); //lo dejo como input revisar entidad de dominio
}
