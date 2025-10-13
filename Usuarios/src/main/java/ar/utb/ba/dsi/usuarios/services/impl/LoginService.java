package ar.utb.ba.dsi.usuarios.services.impl;

import ar.utb.ba.dsi.usuarios.dto.input.CredencialesDTO;
import ar.utb.ba.dsi.usuarios.dto.input.RefreshRequest;
import ar.utb.ba.dsi.usuarios.dto.output.AuthResponseDTO;
import ar.utb.ba.dsi.usuarios.dto.output.RefreshResponse;
import ar.utb.ba.dsi.usuarios.dto.output.UserRolesDTO;
import ar.utb.ba.dsi.usuarios.error.UsuarioContraseniaIncorrecta;
import ar.utb.ba.dsi.usuarios.error.UsuarioDatosFaltantes;
import ar.utb.ba.dsi.usuarios.error.UsuarioNoEncontrado;
import ar.utb.ba.dsi.usuarios.models.entities.Usuario;
import ar.utb.ba.dsi.usuarios.models.repositories.IUsuarioRepository;
import ar.utb.ba.dsi.usuarios.services.ILoginService;
import ar.utb.ba.dsi.usuarios.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class LoginService implements ILoginService {

  private final IUsuarioRepository usuariosRepository;
  private final BCryptPasswordEncoder passwordEncoder;

  public LoginService(IUsuarioRepository usuariosRepository) {
    this.usuariosRepository = usuariosRepository;
    this.passwordEncoder = new BCryptPasswordEncoder();
  }

  @Override
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

  @Override
  public UserRolesDTO obtenerRolesUsuario(String username) {
    Optional<Usuario> usuarioOpt = usuariosRepository.findUsuarioByNombreUsuario(username);

    if (usuarioOpt.isEmpty()) {
      throw new UsuarioNoEncontrado(username);
    }

    Usuario usuario = usuarioOpt.get();

    return UserRolesDTO.builder()
        .username(usuario.getNombreUsuario())
        .rol(usuario.getRol())
        .build();
  }

  @Override
  public RefreshResponse refrescarAccessToken(RefreshRequest request) {
    Claims claims = JwtUtil.extraerClaims(request.getRefreshToken());

    if (!"refresh".equals(claims.get("type", String.class))) {
      throw new IllegalArgumentException("El token proporcionado no es un token de refresco.");
    }

    String username = claims.getSubject();

    Usuario usuario = usuariosRepository.findUsuarioByNombreUsuario(username)
        .orElseThrow(() -> new UsuarioNoEncontrado(username));

    String newAccessToken = JwtUtil.generarAccessToken(usuario);

    return new RefreshResponse(newAccessToken, request.getRefreshToken());
  }

  private void validacionBasica(CredencialesDTO credenciales) {
    String nombreUsuario = credenciales.getNombreUsuario().trim();
    String contrasenia = credenciales.getContrasenia().trim();

    if (nombreUsuario.isEmpty())
      throw new UsuarioDatosFaltantes("El nombre de usuario es obligatorio");

    if (contrasenia.isEmpty())
      throw new UsuarioDatosFaltantes("La contrasenia es obligatoria");
  }

  private Usuario intentarRecuperarUsuario(String nombreUsuario) {
    return usuariosRepository.findUsuarioByNombreUsuario(nombreUsuario).orElseThrow(() -> new UsuarioNoEncontrado(nombreUsuario));
  }

  public String generarAccessToken(Usuario usuario) {
    return JwtUtil.generarAccessToken(usuario);
  }

  public String generarRefreshToken(Usuario usuario) {
    return JwtUtil.generarRefreshToken(usuario);
  }
}
