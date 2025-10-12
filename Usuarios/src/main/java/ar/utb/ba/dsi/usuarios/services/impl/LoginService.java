package ar.utb.ba.dsi.usuarios.services.impl;

import ar.utb.ba.dsi.usuarios.dto.input.CredencialesDTO;
import ar.utb.ba.dsi.usuarios.dto.output.AuthResponseDTO;
import ar.utb.ba.dsi.usuarios.error.UsuarioContraseniaIncorrecta;
import ar.utb.ba.dsi.usuarios.error.UsuarioDatosFaltantes;
import ar.utb.ba.dsi.usuarios.error.UsuarioNoEncontrado;
import ar.utb.ba.dsi.usuarios.models.entities.Usuario;
import ar.utb.ba.dsi.usuarios.models.repositories.IUsuarioRepository;
import ar.utb.ba.dsi.usuarios.services.ILoginService;
import ar.utb.ba.dsi.usuarios.utils.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements ILoginService {
  
  private final IUsuarioRepository usuariosRepository;
  private final BCryptPasswordEncoder passwordEncoder;

  public LoginService(IUsuarioRepository usuariosRepository) {
    this.usuariosRepository = usuariosRepository;
    this.passwordEncoder = new BCryptPasswordEncoder();
  }

  public AuthResponseDTO autenticarUsuario(CredencialesDTO credenciales) {
    validacionBasica(credenciales);

    Usuario usuario = intentarRecuperarUsuario(credenciales.getNombreUsuario());

    if (!passwordEncoder.matches(credenciales.getContrasenia(), usuario.getContrasenia()))
      throw new UsuarioContraseniaIncorrecta("La contrasenia es incorrecta");

    String accessToken = this.generarAccessToken(usuario);
    String refreshToken = this.generarRefreshToken(usuario);

    AuthResponseDTO authResponseDTO = new AuthResponseDTO();
    authResponseDTO.setAccessToken(accessToken);
    authResponseDTO.setRefreshToken(refreshToken);

    return authResponseDTO;
  }

  private void validacionBasica(CredencialesDTO credenciales) {
    String nombreUsuario = credenciales.getNombreUsuario().trim();
    String contrasenia = credenciales.getContrasenia().trim();

    if (nombreUsuario == null || nombreUsuario.isEmpty())
      throw new UsuarioDatosFaltantes("El nombre de usuario es obligatorio");

    if (contrasenia == null || contrasenia.isEmpty())
      throw new UsuarioDatosFaltantes("La contrasenia es obligatoria");
  }

  private Usuario intentarRecuperarUsuario(String nombreUsuario) {
    Usuario usuario = usuariosRepository.findUsuarioByNombreUsuario(nombreUsuario).orElseThrow(() -> new UsuarioNoEncontrado(nombreUsuario));

    return usuario;
  }

  public String generarAccessToken(Usuario usuario) {
    return JwtUtil.generarAccessToken(usuario);
  }

  public String generarRefreshToken(Usuario usuario) {
    return JwtUtil.generarRefreshToken(usuario);
  }
}
