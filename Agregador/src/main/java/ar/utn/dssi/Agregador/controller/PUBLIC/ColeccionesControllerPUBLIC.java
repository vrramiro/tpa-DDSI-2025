package ar.utn.dssi.Agregador.controller.PUBLIC;

import ar.utn.dssi.Agregador.models.DTOs.outputDTO.HechoOutputDTO;
import ar.utn.dssi.Agregador.models.entities.modoNavegacion.ModoNavegacion;
import ar.utn.dssi.Agregador.services.IColeccionService;
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

  @GetMapping("/hechos/{handle}")
  public ResponseEntity<List<HechoOutputDTO>> obtenerHechosDeColeccion(@PathVariable String handle) {
    List<HechoOutputDTO> hechosDeColeccion = coleccionService.obtenerHechosDeColeccion(handle);

    if(hechosDeColeccion.isEmpty()){
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // status 204
    }

    return ResponseEntity.ok(hechosDeColeccion); // status 200
  }

  //TODO esto esta mal,post con body
  @GetMapping("/hechos/navegacion/{handle}")
  public ResponseEntity<List<HechoOutputDTO>> obtenerHechos(@ModelAttribute ModoNavegacion modoNavegacion, @PathVariable String handle) {
    List<HechoOutputDTO> hechos = coleccionService.navegacionColeccion(modoNavegacion, handle);

    if(hechos.isEmpty()) {
      return ResponseEntity.noContent().build(); // status 204
    }

    return ResponseEntity.ok(hechos); // status 200
  }
}
