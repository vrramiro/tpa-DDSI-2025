package ar.utn.dssi.FuenteProxy.models.mappers;


import ar.utn.dssi.FuenteProxy.models.DTOs.input.CategoriaInputDTO;
import ar.utn.dssi.FuenteProxy.models.entities.Categoria;

public class MapperDeCategorias {
    public static Categoria categoriaFromInputDTO(CategoriaInputDTO categoriaInputDTO) {
        Categoria categoria = new Categoria();

        categoria.setNombre(categoriaInputDTO.getNombre());

        return categoria;
    }
}
