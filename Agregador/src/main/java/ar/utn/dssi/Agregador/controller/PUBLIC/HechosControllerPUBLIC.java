package ar.utn.dssi.Agregador.controller.PUBLIC;

import ar.utn.dssi.Agregador.dto.output.HechoOutputDTO;
import ar.utn.dssi.Agregador.services.IHechosService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/hechos")
@RequiredArgsConstructor
public class HechosControllerPUBLIC {
  private final IHechosService hechosService;

  @GetMapping
  public ResponseEntity<List<HechoOutputDTO>> obtenerHechos(
      @RequestParam(name = "fechaReporteDesde", required = false)
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaReporteDesde,
      @RequestParam(name = "fechaReporteHasta", required = false)
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaReporteHasta,
      @RequestParam(name = "fechaAcontecimientoDesde", required = false)
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaAcontecimientoDesde,
      @RequestParam(name = "fechaAcontecimientoHasta", required = false)
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaAcontecimientoHasta,
      @RequestParam(name = "id_categoria", required = false) Long idCategoria,
      @RequestParam(name = "provincia", required = false) String provincia
  ) {
    List<HechoOutputDTO> hechos = hechosService.obtenerHechos(
            fechaReporteDesde,
            fechaReporteHasta,
            fechaAcontecimientoDesde,
            fechaAcontecimientoHasta,
            idCategoria,
            provincia
    );

    if (hechos.isEmpty()) {
      return ResponseEntity.noContent().build();
    }

    return ResponseEntity.ok(hechos);
  }

  @GetMapping("/{idHecho}")
  public ResponseEntity<HechoOutputDTO> obtenerHechoPorId(@PathVariable Long idHecho) {
    HechoOutputDTO hecho = hechosService.obtenerHechoPorId(idHecho);

    if (hecho == null) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(hecho);
  }
}
