package ar.utb.ba.dsi.Normalizador.controllers;

import ar.utb.ba.dsi.Normalizador.models.DTOs.Input.CategoriaInputDTO;
import ar.utb.ba.dsi.Normalizador.models.DTOs.Output.CategoriaOutputDTO;
import ar.utb.ba.dsi.Normalizador.service.ICategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {
    @Autowired
    private ICategoriaService categoriaService;

    @GetMapping("/normalizar")
    public ResponseEntity<CategoriaOutputDTO> normalizarCategoria(@RequestBody CategoriaInputDTO categoria) {
        CategoriaOutputDTO categoriaOutputDTO = categoriaService.normalizarCategoriaOutPut(categoria);
        return ResponseEntity.ok(categoriaOutputDTO);
    }

    @GetMapping("/categorias")
    public ResponseEntity<List<CategoriaOutputDTO>> listarCategorias(){
        List<CategoriaOutputDTO> categorias = categoriaService.obtenerCategorias();
        return ResponseEntity.ok(categorias);
    }
}
