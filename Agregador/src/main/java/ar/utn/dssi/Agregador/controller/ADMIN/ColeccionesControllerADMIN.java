package ar.utn.dssi.Agregador.controller.ADMIN;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.ColeccionInputDTO;
import ar.utn.dssi.Agregador.models.DTOs.outputDTO.ColeccionOutputDTO;
import ar.utn.dssi.Agregador.models.DTOs.outputDTO.HechoOutputDTO;
import ar.utn.dssi.Agregador.models.entities.algoritmoConsenso.AlgoritmoConsenso;
import ar.utn.dssi.Agregador.services.IColeccionService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/colecciones")
public class ColeccionesControllerADMIN {
  @Autowired
  private IColeccionService coleccionService;

  //OPERACIONES CRUD SOBRE COLECCIONES
  @PostMapping("/crear")
  public ResponseEntity<ColeccionOutputDTO> crearColeccion(@RequestBody ColeccionInputDTO coleccionInputDTO){
    ColeccionOutputDTO coleccionOutputDTO = coleccionService.crearColeccion(coleccionInputDTO);

    return ResponseEntity.status(HttpStatus.CREATED).body(coleccionOutputDTO); // status 201
  }

  @GetMapping
  public ResponseEntity<List<ColeccionOutputDTO>> obtenerColecciones(){
    List<ColeccionOutputDTO> colecciones = coleccionService.obtenerColecciones();

    if(colecciones.isEmpty()){
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); //status 204
    }

    return ResponseEntity.ok(colecciones); // status 200
  }

  @GetMapping("/hechos/{idColeccion}")
  public ResponseEntity<List<HechoOutputDTO>> obtenerHechosDeColeccion(@PathVariable String idColeccion) {
    List<HechoOutputDTO> hechosDeColeccion = coleccionService.hechosDeColeccion(idColeccion);

    if(hechosDeColeccion.isEmpty()){
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // status 204
    }

    return ResponseEntity.ok(hechosDeColeccion); // status 200
  }



  @PutMapping("/actualizar/{idColeccion}")
  public ResponseEntity<ColeccionOutputDTO> actualizarColeccion(@PathVariable String handle, @RequestBody ColeccionInputDTO coleccionInputDTO){
    ColeccionOutputDTO coleccionOutputDTO = coleccionService.actualizarColeccion(handle, coleccionInputDTO);

    return ResponseEntity.status(HttpStatus.ACCEPTED).body(coleccionOutputDTO); // status 201
  }

  @DeleteMapping("/eliminar/{idColeccion}")
  public ResponseEntity<Void> eliminarColeccion(@PathVariable String handle){
    coleccionService.eliminarColeccion(handle);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // status 204
  }

  //AGREGAR O QUITAR FUENTES DE UNA COLECCION
  @PutMapping("/agregar/{idColeccion}/{idFuente}")
  public ResponseEntity<Void> agregarFuente(@PathVariable Long idFuente, @PathVariable String handle) {
    coleccionService.agregarFuente(idFuente, handle);

    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/eliminar/{idColeccion}/{idFuente}")
  public ResponseEntity<Void> eliminarFuente(@PathVariable Long idFuente, @PathVariable String handle){
    coleccionService.eliminarFuente(idFuente, handle);

    return ResponseEntity.ok().build(); // status 200
  }

  //ESTABLECER EL ALGORITMO DE CONSENSO
  @PutMapping("/actualizar/{handle}")
  public ResponseEntity<Void> actualizarAlgoritmo(@PathVariable String handle, @RequestBody AlgoritmoConsenso algoritmoConsenso) {
    coleccionService.actualizarAlgoritmo(handle, algoritmoConsenso);

    return ResponseEntity.status(HttpStatus.ACCEPTED).build(); // 202 Accepted
  }

}
