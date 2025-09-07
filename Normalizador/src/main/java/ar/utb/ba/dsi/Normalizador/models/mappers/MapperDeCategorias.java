package ar.utb.ba.dsi.Normalizador.models.mappers;

import ar.utb.ba.dsi.Normalizador.models.DTOs.Output.CategoriaOutputDTO;
import ar.utb.ba.dsi.Normalizador.models.entities.Categoria;

public class MapperDeCategorias {
    public static CategoriaOutputDTO categoriaToOutputDTO(Categoria categoria) {
        CategoriaOutputDTO categoriaOutputDTO = new CategoriaOutputDTO();
            categoriaOutputDTO.setId(categoria.getId());
            categoriaOutputDTO.setCategoria(categoria.getNombre());
        return null;
    }

    public static Categoria categoriaFromOutputDTO(CategoriaOutputDTO categoriaOutputDTO) {
        Categoria categoria = new Categoria();
            categoria.setId(categoriaOutputDTO.getId());
            categoria.setNombre(categoriaOutputDTO.getCategoria());
        return categoria;
    }
}
