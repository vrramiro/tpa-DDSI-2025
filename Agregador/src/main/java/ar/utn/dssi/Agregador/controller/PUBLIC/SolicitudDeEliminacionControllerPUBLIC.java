package ar.utn.dssi.Agregador.controller.PUBLIC;

import ar.utn.dssi.Agregador.dto.input.SolicitudDeEliminacionInputDTO;
import ar.utn.dssi.Agregador.dto.output.SolicitudDeEliminacionOutputDTO;
import ar.utn.dssi.Agregador.services.impl.SolicitudDeEliminacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
