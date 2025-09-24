package ar.utn.dssi.FuenteProxy.controller;

import ar.utn.dssi.FuenteProxy.models.DTOs.output.HechoOutputDTO;
import ar.utn.dssi.FuenteProxy.service.IHechosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/hechos")
public class HechosController {
  @Autowired
  public IHechosService hechosService;

  @GetMapping
  public ResponseEntity<List<HechoOutputDTO>> obtenerHechos() {

    List<HechoOutputDTO> hechos = this.hechosService.obtenerHechos();

    return ResponseEntity.ok(hechos);
  }

  @PostMapping("/importar")
  public ResponseEntity<Void> importarHechos() {
    this.hechosService.importarHechos();
    return ResponseEntity.ok().build();
  }

  //TODO: Revisar otras operaciones
}
