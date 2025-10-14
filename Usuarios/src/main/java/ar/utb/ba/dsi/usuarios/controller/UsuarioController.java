package ar.utb.ba.dsi.usuarios.controller;

import ar.utb.ba.dsi.usuarios.dto.UsuarioDTO;
import ar.utb.ba.dsi.usuarios.error.UsuarioDatosFaltantes;
import ar.utb.ba.dsi.usuarios.error.UsuarioDuplicadoExcepcion;
import ar.utb.ba.dsi.usuarios.services.IUsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
  private final IUsuarioService usuarioService;

  @PostMapping
  public ResponseEntity<?> crearUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) {
    try {
      usuarioService.crearUsuario(usuarioDTO);
      return ResponseEntity.status(HttpStatus.CREATED).build();
    } catch (UsuarioDuplicadoExcepcion e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("mensaje", e.getMessage()));
    } catch (UsuarioDatosFaltantes e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
              .body(Map.of("mensaje", e.getMessage(), "errores", e.getFieldErrors()));
    }
  }

}
