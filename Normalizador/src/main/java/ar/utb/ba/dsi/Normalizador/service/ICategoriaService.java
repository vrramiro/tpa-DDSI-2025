package ar.utb.ba.dsi.Normalizador.service;

import ar.utb.ba.dsi.Normalizador.dto.Input.CategoriaInputDTO;
import ar.utb.ba.dsi.Normalizador.dto.output.CategoriaOutputDTO;
import ar.utb.ba.dsi.Normalizador.models.entities.Categoria;
import java.util.List;

public interface ICategoriaService {
  CategoriaOutputDTO normalizarCategoriaOutPut(CategoriaInputDTO categoria);

  Categoria normalizarCategoria(String categoriaInput);

  List<CategoriaOutputDTO> obtenerCategorias();

  CategoriaOutputDTO obtenerCategoriaPorId(Long idCategoria);
}
