package ar.utn.dssi.FuenteEstatica.mappers;

import ar.utn.dssi.FuenteEstatica.dto.input.CategoriaInputDTO;
import ar.utn.dssi.FuenteEstatica.dto.output.CategoriaOutputDTO;
import ar.utn.dssi.FuenteEstatica.models.entities.contenido.Categoria;

public class MapperDeCategoria {

  public static Categoria categoriaFromInput(CategoriaInputDTO categoriaInputDTO) {
    Categoria categoria = new Categoria();
    categoria.setIdCategoria(categoriaInputDTO.getId());
    categoria.setCategoria(categoriaInputDTO.getCategoria());
    return categoria;
  }

  public static CategoriaOutputDTO outputDTOFromCategoria(Categoria categoria) {
    CategoriaOutputDTO categoriaOutputDTO = new CategoriaOutputDTO();
    categoriaOutputDTO.setId(categoria.getIdCategoria());
    categoriaOutputDTO.setNombre(categoria.getCategoria());
    return categoriaOutputDTO;
  }
}
