package ar.utn.dssi.Agregador.controller.ADMIN;

import ar.utn.dssi.Agregador.models.DTOs.outputDTO.SolicitudDeEliminacionOutputDTO;
import ar.utn.dssi.Agregador.services.impl.SolicitudDeEliminacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/solicitudes")
public class SolicitudDeEliminacionControllerADMIN {
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
  @PostMapping("/{idSolicitud}/aceptar")
  public ResponseEntity<Void> aceptarSolicitud(@PathVariable Long idSolicitud) {
    solicitudesService.aceptarSolicitud(idSolicitud);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/{idSolicitud}/rechazar")
  public ResponseEntity<Void> rechazarSolicitud(@PathVariable Long idSolicitud) {
    solicitudesService.rechazarSolicitud(idSolicitud);
    return ResponseEntity.ok().build();
  }


}
