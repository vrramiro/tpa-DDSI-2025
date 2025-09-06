package ar.utb.ba.dsi.Normalizador.controllers;

import ar.utb.ba.dsi.Normalizador.models.DTOs.Input.CategoriaInputDTO;
import ar.utb.ba.dsi.Normalizador.models.DTOs.Output.CategoriaOutputDTO;
import ar.utb.ba.dsi.Normalizador.service.ICategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {
    @Autowired
    private ICategoriaService categoriaService;

    @GetMapping("/normalizar")
    public ResponseEntity<CategoriaOutputDTO> normalizarCategoria(@RequestBody CategoriaInputDTO categoria) {
        CategoriaOutputDTO categoriaOutputDTO = categoriaService.normalizarCategoria(categoria);
        return ResponseEntity.ok(categoriaOutputDTO);
    }
}
