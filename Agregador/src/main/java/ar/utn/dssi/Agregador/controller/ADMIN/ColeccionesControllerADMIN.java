package ar.utn.dssi.Agregador.controller.ADMIN;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.ColeccionInputDTO;
import ar.utn.dssi.Agregador.models.DTOs.outputDTO.ColeccionOutputDTO;
import ar.utn.dssi.Agregador.models.DTOs.outputDTO.HechoOutputDTO;
import ar.utn.dssi.Agregador.services.IColeccionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/admin/colecciones")
public class ColeccionesControllerADMIN {
  private final IColeccionService coleccionService;

  public ColeccionesControllerADMIN(IColeccionService coleccionService) {
    this.coleccionService = coleccionService;
  }

  //OPERACIONES CRUD SOBRE COLECCIONES
  @PostMapping
  public ResponseEntity<ColeccionOutputDTO> crearColeccion(@RequestBody ColeccionInputDTO coleccionInputDTO) {
    ColeccionOutputDTO coleccionOutputDTO = coleccionService.crearColeccion(coleccionInputDTO);

    return ResponseEntity.status(HttpStatus.CREATED).body(coleccionOutputDTO); // status 201
  }

  @GetMapping
  public ResponseEntity<List<ColeccionOutputDTO>> obtenerColecciones() {
    List<ColeccionOutputDTO> colecciones = coleccionService.obtenerColecciones();

    if(colecciones.isEmpty()){
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // status 204
    }

    return ResponseEntity.ok(colecciones); // status 200
  }

  @PutMapping("/{handle}")
  public ResponseEntity<ColeccionOutputDTO> actualizarColeccion(@PathVariable String handle, @RequestBody ColeccionInputDTO coleccionInputDTO) {
    ColeccionOutputDTO coleccionOutputDTO = coleccionService.editarColeccion(handle, coleccionInputDTO);

    return ResponseEntity.status(HttpStatus.ACCEPTED).body(coleccionOutputDTO); // status 201
  }

  @DeleteMapping("/{handle}")
  public ResponseEntity<Void> eliminarColeccion(@PathVariable String handle) {
    coleccionService.eliminarColeccion(handle);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // status 204
  }

  @GetMapping("/{handle}/hechos")
  public ResponseEntity<List<HechoOutputDTO>> obtenerHechos
      (@PathVariable String handle,
       @RequestParam(name = "modoNavegacion", defaultValue = "MODO_IRRESTRICTO") String modoNavegacion,
       @RequestParam(name = "fechaReporteDesde", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaReporteDesde,
       @RequestParam(name = "fechaReporteHasta", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaReporteHasta,
       @RequestParam(name = "fechaAcontecimientoDesde", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaAcontecimientoDesde,
       @RequestParam(name = "fechaAcontecimientoHasta", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaAcontecimientoHasta,
       @RequestParam(name = "ciudad", required = false) String ciudad,
       @RequestParam(name = "provincia", required = false) String provincia
      ) {
    List<HechoOutputDTO> hechos = coleccionService.obtenerHechosDeColeccion(
        modoNavegacion,
        handle,
        fechaReporteDesde,
        fechaReporteHasta,
        fechaAcontecimientoDesde,
        fechaAcontecimientoHasta,
        provincia,
        ciudad);

    if(hechos.isEmpty()) {
      return ResponseEntity.noContent().build(); // status 204
    }

    return ResponseEntity.ok(hechos); // status 200
  }
}
