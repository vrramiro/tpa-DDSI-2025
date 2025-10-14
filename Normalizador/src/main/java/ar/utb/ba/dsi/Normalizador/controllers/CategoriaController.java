package ar.utb.ba.dsi.Normalizador.controllers;

import ar.utb.ba.dsi.Normalizador.dto.Input.CategoriaInputDTO;
import ar.utb.ba.dsi.Normalizador.dto.output.CategoriaOutputDTO;
import ar.utb.ba.dsi.Normalizador.service.ICategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/categoria")
@RequiredArgsConstructor
public class CategoriaController {

  private final ICategoriaService categoriaService;

  @PostMapping("/normalizar")
  public ResponseEntity<CategoriaOutputDTO> normalizarCategoria(@RequestBody CategoriaInputDTO categoria) {
    CategoriaOutputDTO categoriaOutputDTO = categoriaService.normalizarCategoriaOutPut(categoria);
    return ResponseEntity.ok(categoriaOutputDTO);
  }

  @GetMapping("/categorias")
  public ResponseEntity<List<CategoriaOutputDTO>> listarCategorias() {
    List<CategoriaOutputDTO> categorias = categoriaService.obtenerCategorias();
    return ResponseEntity.ok(categorias);
  }

  @GetMapping("/{idCategoria}")
  public ResponseEntity<CategoriaOutputDTO> obtenerCategoriaPorId(@PathVariable Long idCategoria) {
    CategoriaOutputDTO categoria = categoriaService.obtenerCategoriaPorId(idCategoria);
    return ResponseEntity.ok(categoria);
  }
}
