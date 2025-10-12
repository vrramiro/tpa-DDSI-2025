package ar.utn.dssi.FuenteDinamica.controllers;

import ar.utn.dssi.FuenteDinamica.models.DTOs.inputs.HechoInputDTO;
import ar.utn.dssi.FuenteDinamica.models.DTOs.outputs.HechoOutputDTO;
import ar.utn.dssi.FuenteDinamica.services.IHechosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/hechos")

public class HechosController {
  @Autowired
  private IHechosService hechosService;

  @PostMapping
  public ResponseEntity<Void> crearHecho(@RequestBody HechoInputDTO hecho) {
    this.hechosService.crear(hecho);
    return ResponseEntity.ok().build();
  }

  @GetMapping
  public ResponseEntity<List<HechoOutputDTO>> obtenerHechosNuevos
          (@RequestParam(name = "fechaDesde", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaDesde) {
    List<HechoOutputDTO> hechos = this.hechosService.obtenerHechos(fechaDesde);
    return ResponseEntity.ok(hechos);
  }

  @PatchMapping("/{idHecho}/visibilidad")
  public ResponseEntity<Void> actualizarVisibilidad(@PathVariable Long idHecho, @RequestParam Boolean visibilidad) {
    this.hechosService.actualizarVisibilidad(idHecho,visibilidad);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/{idHecho}")
  public ResponseEntity<Void> editarHecho(@PathVariable Long idHecho, @RequestBody HechoInputDTO hecho) {
    this.hechosService.editarHecho(hecho, idHecho);
    return ResponseEntity.ok().build();
  }
}

