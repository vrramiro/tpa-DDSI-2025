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

  //OPERACIONES CRUD SOBRE COLECCIONES
  @PostMapping("/hecho")
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

  @GetMapping("/hechos/{handle}")
  public ResponseEntity<List<HechoOutputDTO>> obtenerHechosDeColeccion(@PathVariable String handle) {
    List<HechoOutputDTO> hechosDeColeccion = coleccionService.hechosDeColeccion(handle);

    if(hechosDeColeccion.isEmpty()){
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // status 204
    }

    return ResponseEntity.ok(hechosDeColeccion); // status 200
  }

  @PutMapping("/coleccion/{handle}")
  public ResponseEntity<ColeccionOutputDTO> actualizarColeccion(@PathVariable String handle, @RequestBody ColeccionInputDTO coleccionInputDTO){
    ColeccionOutputDTO coleccionOutputDTO = coleccionService.actualizarColeccion(handle, coleccionInputDTO);

    return ResponseEntity.status(HttpStatus.ACCEPTED).body(coleccionOutputDTO); // status 201
  }

  @DeleteMapping("/coleccion/{handle}")
  public ResponseEntity<Void> eliminarColeccion(@PathVariable String handle){
    coleccionService.eliminarColeccion(handle);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // status 204
  }
}
