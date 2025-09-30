package ar.utn.dssi.FuenteDinamica.models.mappers;


import ar.utn.dssi.FuenteDinamica.models.DTOs.inputs.CategoriaInputDTO;
import ar.utn.dssi.FuenteDinamica.models.entities.Categoria;

public class MapperDeCategorias {
    public static Categoria categoriaFromInputDTO(CategoriaInputDTO categoriaInputDTO) {
        Categoria categoria = new Categoria();
            categoria.setNombre(categoriaInputDTO.getNombre());
        return categoria;
    }


}
