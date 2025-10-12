package ar.utb.ba.dsi.usuarios.services;

import ar.utb.ba.dsi.usuarios.dto.input.CredencialesDTO;
import ar.utb.ba.dsi.usuarios.dto.output.AuthResponseDTO;

public interface ILoginService {
  AuthResponseDTO autenticarUsuario(CredencialesDTO credencialesDTO);
}
