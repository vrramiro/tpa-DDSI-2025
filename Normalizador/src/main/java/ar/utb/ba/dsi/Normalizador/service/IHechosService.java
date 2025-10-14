package ar.utb.ba.dsi.Normalizador.service;

import ar.utb.ba.dsi.Normalizador.dto.Input.HechoInputDTO;
import ar.utb.ba.dsi.Normalizador.dto.output.HechoOutputDTO;

public interface IHechosService {
  public HechoOutputDTO normalizarHecho(HechoInputDTO hecho);
}
