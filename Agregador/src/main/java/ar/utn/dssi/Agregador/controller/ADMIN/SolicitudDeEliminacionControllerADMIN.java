package ar.utn.dssi.Agregador.controller.ADMIN;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.SolicitudProcesadaInputDTO;
import ar.utn.dssi.Agregador.models.DTOs.outputDTO.SolicitudDeEliminacionOutputDTO;
import ar.utn.dssi.Agregador.services.impl.SolicitudDeEliminacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/admin/solicitudes")
public class SolicitudDeEliminacionControllerADMIN {
  @Autowired
  private SolicitudDeEliminacionService solicitudesService;

  @GetMapping
  public ResponseEntity<List<SolicitudDeEliminacionOutputDTO>> obtenerSolicitudes(@RequestParam(name = "estado", required = false) String estado,
                                                                                  @RequestParam(name = "spam", required = false) Boolean spam) {
    List<SolicitudDeEliminacionOutputDTO> solicitudesObtenidas = solicitudesService.obtenerSolicitudes(estado, spam);

    if (solicitudesObtenidas.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // status 204
    }
    return ResponseEntity.ok(solicitudesObtenidas); // status 200
  }

  @PostMapping("/procesar/{idSolicitud}")
  public ResponseEntity<SolicitudDeEliminacionOutputDTO> procesarSolicitud(@PathVariable Long idSolicitud, @RequestBody SolicitudProcesadaInputDTO solicitud) {
    solicitudesService.procesarSolicitud(idSolicitud, solicitud);
    return ResponseEntity.ok().build();
  }
}
