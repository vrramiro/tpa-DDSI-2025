package ar.utn.dssi.Agregador.controller.PUBLIC;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.FiltroInputDTO;
import ar.utn.dssi.Agregador.models.entities.Filtro;
import ar.utn.dssi.Agregador.models.DTOs.outputDTO.HechoOutputDTO;
import ar.utn.dssi.Agregador.models.entities.modoNavegacion.IModoNavegacion;
import ar.utn.dssi.Agregador.services.IHechosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/hechos")
public class HechosControllerPUBLIC {
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
}




