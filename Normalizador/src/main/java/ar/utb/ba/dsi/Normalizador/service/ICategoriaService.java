package ar.utb.ba.dsi.Normalizador.service;

import ar.utb.ba.dsi.Normalizador.models.DTOs.Input.CategoriaInputDTO;
import ar.utb.ba.dsi.Normalizador.models.DTOs.Output.CategoriaOutputDTO;

public interface ICategoriaService {
    public CategoriaOutputDTO normalizarCategoria(CategoriaInputDTO categoria);

}
