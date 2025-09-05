package ar.utb.ba.dsi.Normalizador.controllers;

import ar.utb.ba.dsi.Normalizador.models.DTOs.UbicacionResponse;
import ar.utb.ba.dsi.Normalizador.service.INormalizadorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/solicitudes")
public class UbicacionController {

    private final INormalizadorService normalizadorService;

    public UbicacionController(INormalizadorService normalizadorService) {
        this.normalizadorService = normalizadorService;
    }

    @GetMapping("/ubicacion")
    public ResponseEntity<UbicacionResponse> obtenerUbicacion(@RequestParam Double latitud, @RequestParam Double longitud) {
        UbicacionResponse ubicacion = normalizadorService.obtenerUbicacion(latitud, longitud);
        return ResponseEntity.ok(ubicacion);
    }

}
