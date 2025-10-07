package ar.utn.dssi.Agregador.controller.PUBLIC;

import ar.utn.dssi.Agregador.models.DTOs.outputDTO.HechoOutputDTO;
import ar.utn.dssi.Agregador.services.IColeccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/public/colecciones")
public class ColeccionesControllerPUBLIC {
  @Autowired
  private IColeccionService coleccionService;

  @GetMapping("/{handle}/hechos")
  public ResponseEntity<List<HechoOutputDTO>> obtenerHechos
    (@PathVariable String handle,
     @RequestParam(name = "modoNavegacion", defaultValue = "MODO_CURADO") String modoNavegacion,
     @RequestParam(name = "fechaReporteDesde", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaReporteDesde,
     @RequestParam(name = "fechaReporteHasta", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaReporteHasta,
     @RequestParam(name = "fechaAcontecimientoDesde", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaAcontecimientoDesde,
     @RequestParam(name = "fechaAcontecimientoHasta", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaAcontecimientoHasta,
     @RequestParam(name = "ciudad", required = false) String ciudad,
     @RequestParam(name = "provincia", required = false) String provincia,
     @RequestParam(name = "fuente", required = false) Long fuenteId
    ) {
    List<HechoOutputDTO> hechos = coleccionService.obtenerHechosDeColeccion(
        modoNavegacion,
        handle,
        fechaReporteDesde,
        fechaReporteHasta,
        fechaAcontecimientoDesde,
        fechaAcontecimientoHasta,
        provincia,
        ciudad,
        fuenteId);

    if(hechos.isEmpty()) {
      return ResponseEntity.noContent().build(); // status 204
    }

    return ResponseEntity.ok(hechos); // status 200
  }
}
