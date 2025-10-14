package ar.utb.ba.dsi.Normalizador.service;

import ar.utb.ba.dsi.Normalizador.dto.Input.CategoriaInputDTO;
import ar.utb.ba.dsi.Normalizador.dto.output.CategoriaOutputDTO;
import ar.utb.ba.dsi.Normalizador.models.entities.Categoria;
import java.util.List;

public interface ICategoriaService {
  public CategoriaOutputDTO normalizarCategoriaOutPut(CategoriaInputDTO categoria);

  public Categoria normalizarCategoria(String categoriaInput);

  public List<CategoriaOutputDTO> obtenerCategorias();

  public CategoriaOutputDTO obtenerCategoriaPorId(Long idCategoria);
}
