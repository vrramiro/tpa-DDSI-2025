package ar.utn.dssi.FuenteProxy.mappers;


import ar.utn.dssi.FuenteProxy.dto.input.CategoriaInputDTONormalizador;
import ar.utn.dssi.FuenteProxy.models.entities.Categoria;

public class MapperDeCategorias {
  public static Categoria categoriaFromInputDTO(CategoriaInputDTONormalizador categoriaInputDTO) {
    Categoria categoria = new Categoria();

    categoria.setIdCategoria(categoriaInputDTO.getIdCategoria());
    categoria.setNombre(categoriaInputDTO.getNombre());

    return categoria;
  }
}
