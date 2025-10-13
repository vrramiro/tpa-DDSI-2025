package ar.utb.ba.dsi.usuarios.controller;

import ar.utb.ba.dsi.usuarios.dto.input.CredencialesDTO;
import ar.utb.ba.dsi.usuarios.dto.input.RefreshRequest;
import ar.utb.ba.dsi.usuarios.dto.output.AuthResponseDTO;
import ar.utb.ba.dsi.usuarios.dto.output.RefreshResponse;
import ar.utb.ba.dsi.usuarios.dto.output.UserRolesDTO;
import ar.utb.ba.dsi.usuarios.error.UsuarioNoEncontrado;
import ar.utb.ba.dsi.usuarios.services.impl.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {

  private final LoginService loginService;

  @PostMapping
  public ResponseEntity<AuthResponseDTO> auth(@RequestBody CredencialesDTO credenciales) {
    log.info("Me llego:" + credenciales.getContrasenia(), credenciales.getNombreUsuario());
    AuthResponseDTO authResponseDTO = loginService.autenticarUsuario(credenciales);
    log.info("El usuario {} está logueado. El token generado es {}", credenciales.getNombreUsuario(), authResponseDTO.getAccessToken());
    return ResponseEntity.ok(authResponseDTO);
  }

  @PostMapping("/refresh")
  public ResponseEntity<RefreshResponse> refreshToken(@RequestBody RefreshRequest request) {
    RefreshResponse response = loginService.refrescarAccessToken(request);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/user/rol")
  public ResponseEntity<UserRolesDTO> getUserRol(Authentication authentication) {
    try {
      String username = authentication.getName();
      UserRolesDTO response = loginService.obtenerRolesUsuario(username);
      return ResponseEntity.ok(response);
    } catch (UsuarioNoEncontrado e) {
      return ResponseEntity.notFound().build();
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

}
