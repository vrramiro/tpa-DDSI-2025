package ar.utb.ba.dsi.usuarios.controller;

import ar.utb.ba.dsi.usuarios.dto.UsuarioDTO;
import ar.utb.ba.dsi.usuarios.services.IUsuarioService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
    private IUsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Void> crearUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            usuarioService.crearUsuario(usuarioDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


}
