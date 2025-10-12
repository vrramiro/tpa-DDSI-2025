package ar.utb.ba.dsi.usuarios.controller;

import ar.utb.ba.dsi.usuarios.dto.input.CredencialesDTO;
import ar.utb.ba.dsi.usuarios.dto.output.AuthResponseDTO;
import ar.utb.ba.dsi.usuarios.dto.output.UserRolesDTO;
import ar.utb.ba.dsi.usuarios.error.UsuarioNoEncontrado;
import ar.utb.ba.dsi.usuarios.services.impl.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {
  private final LoginService loginService;

  @PostMapping
  public ResponseEntity<AuthResponseDTO> auth(@RequestBody CredencialesDTO credenciales) {
    AuthResponseDTO authResponseDTO = loginService.autenticarUsuario(credenciales);
    log.info("El usuario {} está logueado. El token generado es {}", credenciales.getNombreUsuario(), authResponseDTO.getAccessToken());
    return ResponseEntity.ok(authResponseDTO);
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
