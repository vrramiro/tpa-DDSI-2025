package ar.utb.ba.dsi.Normalizador.mappers;

import ar.utb.ba.dsi.Normalizador.dto.output.CategoriaOutputDTO;
import ar.utb.ba.dsi.Normalizador.models.entities.Categoria;

public class MapperDeCategorias {
  public static CategoriaOutputDTO categoriaToOutputDTO(Categoria categoria) {
    CategoriaOutputDTO categoriaOutputDTO = new CategoriaOutputDTO();
    categoriaOutputDTO.setId(categoria.getId());
    categoriaOutputDTO.setCategoria(categoria.getNombre());
    return categoriaOutputDTO;
  }

}
