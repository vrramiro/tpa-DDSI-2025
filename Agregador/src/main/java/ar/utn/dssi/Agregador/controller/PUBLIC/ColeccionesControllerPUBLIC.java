package ar.utn.dssi.Agregador.controller.PUBLIC;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.ColeccionInputDTO;
import ar.utn.dssi.Agregador.models.DTOs.inputDTO.FiltroInputDTO;
import ar.utn.dssi.Agregador.models.DTOs.outputDTO.ColeccionOutputDTO;
import ar.utn.dssi.Agregador.models.DTOs.outputDTO.HechoOutputDTO;
import ar.utn.dssi.Agregador.models.entities.modoNavegacion.IModoNavegacion;
import ar.utn.dssi.Agregador.models.entities.modoNavegacion.ModoNavegacion;
import ar.utn.dssi.Agregador.services.IColeccionService;
import ar.utn.dssi.Agregador.services.IHechosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public/colecciones")
public class ColeccionesControllerPUBLIC {
  @Autowired
  private IColeccionService coleccionService;

  @GetMapping("/{idColeccion}/hechos")
  public ResponseEntity<List<HechoOutputDTO>> obtenerHechosDeColeccion(@PathVariable String handle) {
    List<HechoOutputDTO> hechosDeColeccion = coleccionService.hechosDeColeccion(handle);

    if(hechosDeColeccion.isEmpty()){
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // status 204
    }

    return ResponseEntity.ok(hechosDeColeccion); // status 200
  }

  @GetMapping
  public ResponseEntity<List<HechoOutputDTO>> obtenerHechos(@ModelAttribute FiltroInputDTO filtros, @ModelAttribute ModoNavegacion modoNavegacion, @PathVariable String handle) {
    List<HechoOutputDTO> hechos = coleccionService.navegacionColeccion(filtros, modoNavegacion, handle);

    if(hechos.isEmpty()) {
      return ResponseEntity.noContent().build(); // status 204
    }

    return ResponseEntity.ok(hechos); // status 200
  }
}
