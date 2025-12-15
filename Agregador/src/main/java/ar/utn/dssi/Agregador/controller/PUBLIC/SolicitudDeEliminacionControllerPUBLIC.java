package ar.utn.dssi.Agregador.controller.PUBLIC;

import ar.utn.dssi.Agregador.dto.input.SolicitudDeEliminacionInputDTO;
import ar.utn.dssi.Agregador.dto.output.SolicitudDeEliminacionOutputDTO;
import ar.utn.dssi.Agregador.services.impl.SolicitudDeEliminacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/solicitudes-eliminacion")
@RequiredArgsConstructor
public class SolicitudDeEliminacionControllerPUBLIC {
  private final SolicitudDeEliminacionService solicitudesService;

  @PostMapping
  public ResponseEntity<SolicitudDeEliminacionOutputDTO> crearSolicitudDeEliminacion(@RequestBody SolicitudDeEliminacionInputDTO solicitudDeEliminacion) {
    SolicitudDeEliminacionOutputDTO solicitud = solicitudesService.crearSolicitudDeEliminacion(solicitudDeEliminacion);

    return ResponseEntity.status(HttpStatus.CREATED).body(solicitud); // status 201
  }

  @GetMapping("/spam-cantidad")
  public ResponseEntity<Long> obtenerCantidadSpam() {
    Long cantidad = solicitudesService.contarSolicitudesSpam();
    return ResponseEntity.ok(cantidad);
  }
}
