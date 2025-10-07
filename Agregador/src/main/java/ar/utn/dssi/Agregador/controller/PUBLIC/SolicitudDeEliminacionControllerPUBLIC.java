package ar.utn.dssi.Agregador.controller.PUBLIC;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.SolicitudDeEliminacionInputDTO;
import ar.utn.dssi.Agregador.models.DTOs.outputDTO.SolicitudDeEliminacionOutputDTO;
import ar.utn.dssi.Agregador.services.impl.SolicitudDeEliminacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/solicitudes")
public class SolicitudDeEliminacionControllerPUBLIC {
  @Autowired
  private SolicitudDeEliminacionService solicitudesService;

  @PostMapping
  public ResponseEntity<SolicitudDeEliminacionOutputDTO> crearSolicitudDeEliminacion(@RequestBody SolicitudDeEliminacionInputDTO solicitudDeEliminacion){
    SolicitudDeEliminacionOutputDTO solicitud = solicitudesService.crearSolicitudDeEliminacion(solicitudDeEliminacion);

    return ResponseEntity.status(HttpStatus.CREATED).body(solicitud); // status 201
  }
}
