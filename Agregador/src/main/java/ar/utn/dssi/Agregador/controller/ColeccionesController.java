package ar.utn.dssi.Agregador.controller;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.ColeccionInputDTO;
import ar.utn.dssi.Agregador.models.DTOs.outputDTO.ColeccionOutputDTO;
import ar.utn.dssi.Agregador.models.DTOs.outputDTO.HechoOutputDTO;
import ar.utn.dssi.Agregador.services.IColeccionService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/colecciones")
public class ColeccionesController {
  @Autowired
  private IColeccionService coleccionService;

  //Falta manejo de errores => no se si tendria que ser con try/catch no hay algo tan particular
  @GetMapping
  public ResponseEntity<List<ColeccionOutputDTO>> obtenerColecciones(){
    List<ColeccionOutputDTO> colecciones = coleccionService.obtenerColecciones();

    if(colecciones.isEmpty()){
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); //status 204
    }

    return ResponseEntity.ok(colecciones); // status 200
  }

  @PostMapping("/crear")
  public ResponseEntity<ColeccionOutputDTO> crearColeccion(@RequestBody ColeccionInputDTO coleccionInputDTO){
      ColeccionOutputDTO coleccionOutputDTO = coleccionService.crearColeccion(coleccionInputDTO); //TODO falta manejo de status revisar TODO del coleccionService

      return ResponseEntity.status(HttpStatus.CREATED).body(coleccionOutputDTO); // status 201
  }

  @PutMapping("/actualizar/{idColeccion}")
  public ResponseEntity<ColeccionOutputDTO> actualizarColeccion(@PathVariable String idColeccion, @RequestBody ColeccionInputDTO coleccionInputDTO){
    ColeccionOutputDTO coleccionOutputDTO = coleccionService.actualizarColeccion(idColeccion, coleccionInputDTO); //TODO implementar actualizarColeccion

    return ResponseEntity.ok(coleccionOutputDTO); // status 200
  }

  @DeleteMapping("/eliminar/{idColeccion}")
  public ResponseEntity<Void> eliminarColeccion(@PathVariable String idColeccion){
    coleccionService.eliminarColeccion(idColeccion);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // status 204
  }

  @GetMapping("/hechos/{idColeccion}")
  public ResponseEntity<List<HechoOutputDTO>> obtenerHechosDeColeccion(@PathVariable String idColeccion) {
    List<HechoOutputDTO> hechosDeColeccion = coleccionService.obtenerHechosDeColeccion(idColeccion);

    if(hechosDeColeccion.isEmpty()){
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // status 204
    }

    return ResponseEntity.ok(hechosDeColeccion); // status 200
  }

}
