package ar.utb.ba.dsi.Normalizador.controllers;

import ar.utb.ba.dsi.Normalizador.models.DTOs.Input.HechoInputDTO;
import ar.utb.ba.dsi.Normalizador.models.DTOs.Output.HechoOutputDTO;
import ar.utb.ba.dsi.Normalizador.service.IHechosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hecho")
public class HechosController {
    @Autowired
    private IHechosService normalizadorService;

    @GetMapping("/curar")
    public ResponseEntity<HechoOutputDTO> curarHecho(@RequestBody HechoInputDTO hecho) {
        HechoOutputDTO hechoOutput = normalizadorService.curarHecho(hecho);
        return ResponseEntity.ok(hechoOutput);
    }

}


