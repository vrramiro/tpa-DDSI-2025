package ar.utn.dssi.FuenteDinamica.controllers;

import ar.utn.dssi.FuenteDinamica.dto.input.HechoInputDTO;
import ar.utn.dssi.FuenteDinamica.dto.output.HechoOutputDTO;
import ar.utn.dssi.FuenteDinamica.services.IHechosService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/hechos")
@RequiredArgsConstructor
public class HechosController {

  private final IHechosService hechosService;

  @PostMapping
  public ResponseEntity<Void> crearHecho(@RequestBody HechoInputDTO hecho) {
    System.out.println(hecho.getCategoria().getNombreCategoria());
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
    this.hechosService.actualizarVisibilidad(idHecho, visibilidad);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/{idHecho}")
  public ResponseEntity<Void> editarHecho(@PathVariable Long idHecho, @RequestBody HechoInputDTO hecho) {
    this.hechosService.editarHecho(hecho, idHecho);
    return ResponseEntity.ok().build();
  }
}

