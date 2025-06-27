package ar.utn.dssi.FuenteDinamica.controllers;

import ar.utn.dssi.FuenteDinamica.models.DTOs.inputs.HechoInputDTO;
import ar.utn.dssi.FuenteDinamica.models.DTOs.outputs.HechoOutputDTO;
import ar.utn.dssi.FuenteDinamica.models.entities.Hecho;
import ar.utn.dssi.FuenteDinamica.services.IHechosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/hechos")

public class HechosController {
  @Autowired
  private IHechosService hechosService;

  @PostMapping("/crear")
  public ResponseEntity<Void> crearHecho(@RequestBody HechoInputDTO hecho) {
    this.hechosService.crear(hecho);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/hechos")
  public ResponseEntity<List<HechoOutputDTO>> obtenerHechos() {
    List<HechoOutputDTO> hechos = this.hechosService.obtenerHechos();
    if (hechos.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(hechos);
  }

  @GetMapping("/hechosNuevos")
  public ResponseEntity<List<HechoOutputDTO>> obtenerHechosNuevos() {
    List<HechoOutputDTO> hechos = this.hechosService.obtenerHechosNuevos();

    if (hechos.isEmpty()){
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(hechos);
  }
}

