package ar.utn.dssi.Agregador.controller.PUBLIC;

import ar.utn.dssi.Agregador.dto.output.ColeccionOutputDTO;
import ar.utn.dssi.Agregador.dto.output.HechoOutputDTO;
import ar.utn.dssi.Agregador.services.IColeccionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;

@RestController
@RequestMapping("/public/colecciones")
@RequiredArgsConstructor
public class ColeccionesControllerPUBLIC {
  private final IColeccionService coleccionService;

  @GetMapping
  public ResponseEntity<Page<ColeccionOutputDTO>> obtenerColecciones(@PageableDefault(page = 0, size = 12) Pageable pageable) {
    Page<ColeccionOutputDTO> colecciones = coleccionService.obtenerColecciones(pageable);

    if (colecciones.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // status 204
    }

    return ResponseEntity.ok(colecciones); // status 200
  }

  @GetMapping("/{handle}")
  public ResponseEntity<ColeccionOutputDTO> obtenerColeccion(@PathVariable String handle) {
    ColeccionOutputDTO coleccion = coleccionService.obtenerColeccion(handle);
    return ResponseEntity.ok(coleccion);
  }

  @GetMapping("/{handle}/hechos")
  public ResponseEntity<Page<HechoOutputDTO>> obtenerHechos(
      @PathVariable String handle,
      // ELIMINAMOS el @RequestParam de modoNavegacion
      @RequestParam(name = "page", defaultValue = "0") int page,
      @RequestParam(name = "size", defaultValue = "9") int size,
      @RequestParam(name = "fechaReporteDesde", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaReporteDesde,
      @RequestParam(name = "fechaReporteHasta", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaReporteHasta,
      @RequestParam(name = "fechaAcontecimientoDesde", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaAcontecimientoDesde,
      @RequestParam(name = "fechaAcontecimientoHasta", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaAcontecimientoHasta,
      @RequestParam(name = "ciudad", required = false) String ciudad,
      @RequestParam(name = "provincia", required = false) String provincia
  ) {

    Pageable pageable = PageRequest.of(
        page, size,
        Sort.by(Sort.Direction.DESC, "fechaAcontecimiento")
            .and(Sort.by(Sort.Direction.DESC, "id"))
    );

    // Pasamos el valor EXACTO de tu Enum: "NAVEGACION_IRRESTRICTA"
    Page<HechoOutputDTO> hechos = coleccionService.obtenerHechosDeColeccion(
        false,
        handle,
        fechaReporteDesde,
        fechaReporteHasta,
        fechaAcontecimientoDesde,
        fechaAcontecimientoHasta,
        provincia,
        ciudad,
        pageable);

    return ResponseEntity.ok(hechos);
  }

}
