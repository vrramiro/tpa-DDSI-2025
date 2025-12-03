package ar.utn.dssi.Agregador.controller.ADMIN;

import ar.utn.dssi.Agregador.dto.output.SolicitudEdicionOutputDTO;
import ar.utn.dssi.Agregador.services.impl.SolicitudDeEdicionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin/solicitudes-edicion")
@RequiredArgsConstructor
public class SolicitudEdicionAdminController {
    private final SolicitudDeEdicionService servicio;

    @GetMapping("/pendientes")
    public ResponseEntity<List<SolicitudEdicionOutputDTO>> listarPendientes() {
        return ResponseEntity.ok(servicio.obtenerPendientesDTO());
    }

    @PostMapping("/{idSolicitud}/procesar")
    public ResponseEntity<Void> procesar(@PathVariable Long idSolicitud, @RequestParam String accion) {
        servicio.procesarSolicitud(idSolicitud, accion);
        return ResponseEntity.ok().build();
    }
}