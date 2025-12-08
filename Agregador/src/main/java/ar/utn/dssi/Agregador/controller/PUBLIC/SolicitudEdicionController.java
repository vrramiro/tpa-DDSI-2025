package ar.utn.dssi.Agregador.controller.PUBLIC;

import ar.utn.dssi.Agregador.dto.input.SolicitudEdicionInputDTO;
import ar.utn.dssi.Agregador.services.impl.SolicitudDeEdicionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/solicitudes-edicion")
@RequiredArgsConstructor
public class SolicitudEdicionController {
    private final SolicitudDeEdicionService servicio;

    @PostMapping("/{idHecho}")
    public ResponseEntity<?> crear(@PathVariable Long idHecho, @RequestBody SolicitudEdicionInputDTO dto) {
        servicio.crearSolicitud(idHecho, dto);
        return ResponseEntity.ok("Solicitud creada.");
    }
}