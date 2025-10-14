package ar.utb.ba.dsi.usuarios.services;

import ar.utb.ba.dsi.usuarios.dto.input.CredencialesDTO;
import ar.utb.ba.dsi.usuarios.dto.input.RefreshRequest;
import ar.utb.ba.dsi.usuarios.dto.output.AuthResponseDTO;
import ar.utb.ba.dsi.usuarios.dto.output.RefreshResponse;
import ar.utb.ba.dsi.usuarios.dto.output.UserRolesDTO;

public interface ILoginService {
  AuthResponseDTO autenticarUsuario(CredencialesDTO credencialesDTO);

  UserRolesDTO obtenerRolesUsuario(String username);

  RefreshResponse refrescarAccessToken(RefreshRequest request);
}
