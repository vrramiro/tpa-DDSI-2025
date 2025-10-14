package ar.utn.dssi.FuenteDinamica.mappers;

import ar.utn.dssi.FuenteDinamica.dto.input.CategoriaNormalizadorDTO;
import ar.utn.dssi.FuenteDinamica.dto.output.CategoriaOutputDTO;
import ar.utn.dssi.FuenteDinamica.models.entities.Categoria;

public class MapperDeCategoria {

  public static Categoria categoriaFromCategoriaNormalizadorDTO(CategoriaNormalizadorDTO categoriaNormalizador) {
    Categoria categoria = new Categoria();
    categoria.setIdCategoria(categoriaNormalizador.getIdCategoria());
    categoria.setNombre(categoriaNormalizador.getNombre());
    return categoria;
  }

  public static CategoriaOutputDTO categoriaToOutputDTO(Categoria categoria) {
    CategoriaOutputDTO categoriaOutputDTO = new CategoriaOutputDTO();
    categoriaOutputDTO.setIdCategoria(categoria.getIdCategoria());
    categoriaOutputDTO.setNombre(categoria.getNombre());
    return categoriaOutputDTO;
  }
}
