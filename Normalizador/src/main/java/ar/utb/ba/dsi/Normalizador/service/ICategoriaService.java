package ar.utb.ba.dsi.Normalizador.service;

import ar.utb.ba.dsi.Normalizador.models.DTOs.Input.CategoriaInputDTO;
import ar.utb.ba.dsi.Normalizador.models.DTOs.Output.CategoriaOutputDTO;
import ar.utb.ba.dsi.Normalizador.models.entities.Categoria;

public interface ICategoriaService {
    public CategoriaOutputDTO normalizarCategoriaOutPut(CategoriaInputDTO categoria);
    public Categoria normalizarCategoria(Categoria categoria);

}
