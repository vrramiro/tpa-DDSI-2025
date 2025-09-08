package ar.utn.dssi.FuenteEstatica.models.mappers;


import ar.utn.dssi.FuenteEstatica.models.DTOs.input.CategoriaInputDTO;
import ar.utn.dssi.FuenteEstatica.models.entities.contenido.Categoria;

public class MapperDeCategorias {
    public static Categoria categoriaFromInputDTO(CategoriaInputDTO categoriaInputDTO) {
        Categoria categoria = new Categoria();
            categoria.setNombre(categoriaInputDTO.getNombre());
        return categoria;
    }


}
