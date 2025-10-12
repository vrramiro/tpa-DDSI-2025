package ar.utn.dssi.FuenteDinamica.mappers;

import ar.utn.dssi.FuenteDinamica.dto.input.CategoriaInputDTO;
import ar.utn.dssi.FuenteDinamica.dto.output.CategoriaOutputDTO;
import ar.utn.dssi.FuenteDinamica.models.entities.Categoria;

public class MapperDeCategoria {
  public static Categoria categoriaFromInputDTO(CategoriaInputDTO categoriaInputDTO) {
    Categoria categoria = new Categoria();
    categoria.setIdCategoria(categoriaInputDTO.getId());
    categoria.setNombre(categoriaInputDTO.getNombreCategoria());
    return categoria;
  }

  public static CategoriaOutputDTO categoriaToOutputDTO(Categoria categoria) {
    CategoriaOutputDTO categoriaOutputDTO = new CategoriaOutputDTO();
    categoriaOutputDTO.setIdCategoria(categoria.getIdCategoria());
    categoriaOutputDTO.setNombre(categoria.getNombre());
    return categoriaOutputDTO;
  }
}
