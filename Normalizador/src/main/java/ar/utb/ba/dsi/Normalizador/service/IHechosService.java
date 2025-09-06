package ar.utb.ba.dsi.Normalizador.service;

import ar.utb.ba.dsi.Normalizador.models.DTOs.Input.HechoInputDTO;
import ar.utb.ba.dsi.Normalizador.models.DTOs.Output.HechoOutputDTO;

public interface IHechosService {
    public HechoOutputDTO curarHecho(HechoInputDTO hecho);
}
