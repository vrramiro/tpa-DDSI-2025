package ar.utb.ba.dsi.Normalizador.models.mappers;

import ar.utb.ba.dsi.Normalizador.models.DTOs.Input.CategoriaInputDTO;
import ar.utb.ba.dsi.Normalizador.models.DTOs.Output.CategoriaOutputDTO;
import ar.utb.ba.dsi.Normalizador.models.entities.Categoria;

public class MapperDeCategorias {
    public static Categoria categoriaFromInputDTO(CategoriaInputDTO categoriaInputDTO) {
        Categoria categoria = new Categoria();
        categoria.setNombre(categoriaInputDTO.getCategoriaExterna());
        return categoria;
    }

    public static CategoriaOutputDTO categoriaToOutputDTO(Categoria categoria) {
        CategoriaOutputDTO categoriaOutputDTO = new CategoriaOutputDTO();
        categoriaOutputDTO.setId(categoria.getId());
        categoriaOutputDTO.setCategoria(categoria.getNombre());
        return categoriaOutputDTO;
    }

}
