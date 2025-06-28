package ar.utn.dssi.Agregador.controller;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.HechoInputDTO;
import ar.utn.dssi.Agregador.models.DTOs.outputDTO.HechoOutputDTO;
import ar.utn.dssi.Agregador.services.IHechosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/hechos")
public class HechosController {
  @Autowired
  private IHechosService hechosService;

  @GetMapping
  public ResponseEntity<List<HechoOutputDTO>> obtenerHechos(){
    List<HechoOutputDTO> hechos = hechosService.obtenerHechos();

    if(hechos.isEmpty()) {
      return ResponseEntity.noContent().build(); // status 204
    }

    return ResponseEntity.ok(hechos); // status 200
  }

  @PutMapping("/actualizar/")
  public ResponseEntity<HechoOutputDTO> actualizarHecho(@PathVariable Long idHecho, @RequestBody HechoInputDTO HechoInputDTO){
    HechoOutputDTO hechoOutputDTO = hechosService.actualizarHechos();
  }
}




