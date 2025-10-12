package ar.utb.ba.dsi.Normalizador.controllers;

import ar.utb.ba.dsi.Normalizador.dto.Input.HechoInputDTO;
import ar.utb.ba.dsi.Normalizador.dto.output.HechoOutputDTO;
import ar.utb.ba.dsi.Normalizador.service.IHechosService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hecho")
public class HechosController {
  private final IHechosService normalizadorService;

  public HechosController(IHechosService normalizadorService) {
    this.normalizadorService = normalizadorService;
  }

  @PostMapping("/normalizar")
  public ResponseEntity<HechoOutputDTO> normalizarHecho(@RequestBody HechoInputDTO hecho) {
    HechoOutputDTO hechoOutput = normalizadorService.normalizarHecho(hecho);

    return ResponseEntity.ok(hechoOutput);
  }
}


