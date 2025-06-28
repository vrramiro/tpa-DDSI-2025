package ar.utn.dssi.Agregador.controller.ADMIN;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.ColeccionInputDTO;
import ar.utn.dssi.Agregador.models.DTOs.outputDTO.ColeccionOutputDTO;
import ar.utn.dssi.Agregador.models.DTOs.outputDTO.HechoOutputDTO;
import ar.utn.dssi.Agregador.services.IColeccionService;
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

  //Falta manejo de errores => no se si tendria que ser con try/catch no hay algo tan particular
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
    List<HechoOutputDTO> hechosDeColeccion = coleccionService.leerColeccion(idColeccion);

    if(hechosDeColeccion.isEmpty()){
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // status 204
    }

    return ResponseEntity.ok(hechosDeColeccion); // status 200
  }

  @PostMapping("/crear")
  public ResponseEntity<ColeccionOutputDTO> crearColeccion(@RequestBody ColeccionInputDTO coleccionInputDTO){
      ColeccionOutputDTO coleccionOutputDTO = coleccionService.crearColeccion(coleccionInputDTO);

      return ResponseEntity.status(HttpStatus.CREATED).body(coleccionOutputDTO); // status 201
  }

  @PutMapping("/actualizar/{idColeccion}")
  public ResponseEntity<ColeccionOutputDTO> actualizarColeccion(@PathVariable String handle, @RequestBody ColeccionInputDTO coleccionInputDTO){
    ColeccionOutputDTO coleccionOutputDTO = coleccionService.actualizarColeccion(handle, coleccionInputDTO);

    return ResponseEntity.status(HttpStatus.ACCEPTED).body(coleccionOutputDTO); // status 201
  }

  @PutMapping("/agregar/{idFuente},{idColeccion}")
  public ResponseEntity<Void> agregarFuente(@PathVariable Long idFuente, String handle) {
    coleccionService.agregarFuente(idFuente, handle);

    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/eliminar/{idColeccion}")
  public ResponseEntity<Void> eliminarColeccion(@PathVariable String handle){
    coleccionService.eliminarColeccion(handle);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // status 204
  }

  @DeleteMapping("/eliminar/{idFuente},{idColeccion}")
  public ResponseEntity<Void> eliminarFuente(@PathVariable Long idFuente, String handle){
    coleccionService.eliminarFuente(idFuente, handle);

    return ResponseEntity.ok().build(); // status 200
  }

}
