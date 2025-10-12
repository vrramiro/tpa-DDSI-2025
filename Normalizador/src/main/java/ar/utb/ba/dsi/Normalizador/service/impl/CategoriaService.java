package ar.utb.ba.dsi.Normalizador.service.impl;

import ar.utb.ba.dsi.Normalizador.dto.Input.CategoriaInputDTO;
import ar.utb.ba.dsi.Normalizador.dto.output.CategoriaOutputDTO;
import ar.utb.ba.dsi.Normalizador.mappers.MapperDeCategorias;
import ar.utb.ba.dsi.Normalizador.models.entities.Categoria;
import ar.utb.ba.dsi.Normalizador.models.entities.errores.CategoriaNoEcontrada;
import ar.utb.ba.dsi.Normalizador.models.repositories.ICategoriaRepository;
import ar.utb.ba.dsi.Normalizador.service.ICategoriaService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService implements ICategoriaService {
  private final ICategoriaRepository categoriaRepository;

  public CategoriaService(ICategoriaRepository categoriaRepository) {
    this.categoriaRepository = categoriaRepository;
  }

  @Override
  public CategoriaOutputDTO normalizarCategoriaOutPut(CategoriaInputDTO categoria) {
    String categoriaNombre = categoria.getCategoriaExterna();
    return MapperDeCategorias.categoriaToOutputDTO(this.normalizarCategoria(categoriaNombre));
  }

  @Override
  public Categoria normalizarCategoria(String categoriaInput) {

    Categoria categoriaNormalizada = categoriaRepository.findCategoriaByNombre(categoriaInput);

    if (categoriaNormalizada == null) {
      categoriaNormalizada = categoriaRepository.findCategoriaByCategoriaExterna(categoriaInput);
    }

    if (categoriaNormalizada == null) {
      System.out.println("Categoria no encontrada");
      throw new CategoriaNoEcontrada("Categoría no encontrada: " + categoriaInput);
    }

    return categoriaNormalizada;
  }


  @Override
  public List<CategoriaOutputDTO> obtenerCategorias() {
    return categoriaRepository.findAll().stream().map(MapperDeCategorias::categoriaToOutputDTO).collect(Collectors.toList());
  }
}
