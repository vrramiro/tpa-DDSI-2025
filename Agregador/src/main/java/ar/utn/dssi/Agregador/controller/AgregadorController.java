package ar.utn.dssi.Agregador.controller;

import ar.utn.dssi.Agregador.models.DTOs.outputDTO.HechoOutputDTO;
import ar.utn.dssi.Agregador.services.IHechosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/hechos")
public class AgregadorController {
  @Autowired
  private IHechosService hechosService;

  @PutMapping("/actualizar")
  public ResponseEntity<Void> actualizarHechos(){
    this.hechosService.actualizarHechos();
    return ResponseEntity.ok().build();
  }

  @GetMapping("/hechos")
  public ResponseEntity<List<HechoOutputDTO>> obtenerHechos(){
    List<HechoOutputDTO> hechos = hechosService.obtenerHechos();
    if(hechos.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(hechos);
  }
}




