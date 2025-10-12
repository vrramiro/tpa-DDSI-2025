package ar.utb.ba.dsi.usuarios.controller;

import ar.utb.ba.dsi.usuarios.dto.input.CredencialesDTO;
import ar.utb.ba.dsi.usuarios.dto.output.AuthResponseDTO;
import ar.utb.ba.dsi.usuarios.services.impl.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
    AuthResponseDTO authResponseDTO = loginService.autenticarUsuario(credenciales);
    log.info("El usuario {} está logueado. El token generado es {}", credenciales.getNombreUsuario(), authResponseDTO.getAccessToken());
    return ResponseEntity.ok(authResponseDTO);
  }


}
