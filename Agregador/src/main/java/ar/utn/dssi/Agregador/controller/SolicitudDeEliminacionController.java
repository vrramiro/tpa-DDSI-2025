package ar.utn.dssi.Agregador.controller;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.SolicitudDeEliminacionInputDTO;
import ar.utn.dssi.Agregador.models.entities.solicitud.SolicitudDeEliminacion;
import ar.utn.dssi.Agregador.services.impl.SolicitudDeEliminacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudDeEliminacionController {
  @Autowired
  private SolicitudDeEliminacionService solicitudesService;

  @GetMapping
  public ResponseEntity<List<SolicitudDeEliminacionOutputDTO>> obtenerSolicitudes(){
    List<SolicitudDeEliminacionOutputDTO> solicitudesObtenidas = solicitudesService.obtenerSolicitudes();

    if (solicitudesObtenidas.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // status 204
    }

    return ResponseEntity.ok(solicitudesObtenidas); // status 200
  }


  //TODO hay dos caminos => se toca aceptar y va al metodo aceptar o le da a cualquier boton y se va a procesar solicitud
  @PostMapping("/aceptar/{idSolicitud}")
  public ResponseEntity<Void> aceptarSolicitud(@PathVariable Long idSolicitud) {
    solicitudesService.aceptarSolicitud(idSolicitud);
  }

  @PostMapping("/rechazar/{idSolicitud}")
  public ResponseEntity<Void> rechazarSolicitud(@PathVariable Long idSolicitud) {
    solicitudesService.rechazarSolicitud(idSolicitud);
  }
}
