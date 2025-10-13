package ar.utb.ba.dsi.usuarios.controller;

import ar.utb.ba.dsi.usuarios.dto.UsuarioDTO;
import ar.utb.ba.dsi.usuarios.services.IUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
  private final IUsuarioService usuarioService;

  @PostMapping
  public ResponseEntity<Void> crearUsuario(@RequestBody UsuarioDTO usuarioDTO) {
    usuarioService.crearUsuario(usuarioDTO);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
