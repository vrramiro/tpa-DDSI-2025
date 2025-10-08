package ar.utn.dssi.Agregador.controller.PUBLIC;

import ar.utn.dssi.Agregador.models.DTOs.outputDTO.HechoOutputDTO;
import ar.utn.dssi.Agregador.services.IHechosService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/hechos")
@RequiredArgsConstructor
public class HechosControllerPUBLIC {
  private final IHechosService hechosService;

  @GetMapping
  public ResponseEntity<List<HechoOutputDTO>> obtenerHechos(
          @RequestParam(name = "fechaReporteDesde", required = false)
          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaReporteDesde,
          @RequestParam(name = "fechaReporteHasta", required = false)
          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaReporteHasta,
          @RequestParam(name = "fechaAcontecimientoDesde", required = false)
          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaAcontecimientoDesde,
          @RequestParam(name = "fechaAcontecimientoHasta", required = false)
          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaAcontecimientoHasta,
          @RequestParam(name = "ciudad", required = false) String ciudad,
          @RequestParam(name = "provincia", required = false) String provincia,
          @RequestParam(name = "fuente", required = false) Long fuenteId
  ){
    List<HechoOutputDTO> hechos = hechosService.obtenerHechos(
            fechaReporteDesde, fechaReporteHasta,
            fechaAcontecimientoDesde, fechaAcontecimientoHasta,
            provincia, ciudad, fuenteId
    );

    if (hechos.isEmpty()) {
      return ResponseEntity.noContent().build();
    }

    return ResponseEntity.ok(hechos);
  }
}
