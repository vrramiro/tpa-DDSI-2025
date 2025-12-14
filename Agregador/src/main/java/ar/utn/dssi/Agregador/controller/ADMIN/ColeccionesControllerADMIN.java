package ar.utn.dssi.Agregador.controller.ADMIN;

import ar.utn.dssi.Agregador.dto.input.ColeccionInputDTO;
import ar.utn.dssi.Agregador.dto.output.ColeccionOutputDTO;
import ar.utn.dssi.Agregador.dto.output.HechoOutputDTO;
import ar.utn.dssi.Agregador.services.IColeccionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;

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
  public ResponseEntity<Page<ColeccionOutputDTO>> obtenerColecciones(@PageableDefault Pageable pageable) {
    Page<ColeccionOutputDTO> colecciones = coleccionService.obtenerColecciones(pageable);

    if (colecciones.isEmpty()) {
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
  public ResponseEntity<Page<HechoOutputDTO>> obtenerHechos( // Cambiado a Page<HechoOutputDTO>
                                                             @PathVariable String handle,
                                                             @RequestParam(name = "modoNavegacion", defaultValue = "NAVEGACION_IRRESTRICTA") String modoNavegacion,
                                                             @RequestParam(name = "page", defaultValue = "0") int page, // Nuevo
                                                             @RequestParam(name = "size", defaultValue = "9") int size,  // Nuevo
                                                             @RequestParam(name = "fechaReporteDesde", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaReporteDesde,
                                                             @RequestParam(name = "fechaReporteHasta", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaReporteHasta,
                                                             @RequestParam(name = "fechaAcontecimientoDesde", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaAcontecimientoDesde,
                                                             @RequestParam(name = "fechaAcontecimientoHasta", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaAcontecimientoHasta,
                                                             @RequestParam(name = "ciudad", required = false) String ciudad,
                                                             @RequestParam(name = "provincia", required = false) String provincia
  ) {

    Pageable pageable = PageRequest.of(page, size, JpaSort.unsafe(Sort.Direction.DESC, "h.fechaAcontecimiento"));

    Page<HechoOutputDTO> hechos = coleccionService.obtenerHechosDeColeccion(
            modoNavegacion,
            handle,
            fechaReporteDesde,
            fechaReporteHasta,
            fechaAcontecimientoDesde,
            fechaAcontecimientoHasta,
            provincia,
            ciudad,
            pageable);

    if (hechos.isEmpty()) {
      return ResponseEntity.ok(hechos);
    }

    return ResponseEntity.ok(hechos);
  }
}
