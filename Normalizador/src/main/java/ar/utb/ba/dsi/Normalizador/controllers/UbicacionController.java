package ar.utb.ba.dsi.Normalizador.controllers;

import ar.utb.ba.dsi.Normalizador.models.DTOs.Output.UbicacionOutputDTO;
import ar.utb.ba.dsi.Normalizador.service.IUbicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/ubicacion")
public class UbicacionController {
    @Autowired
    private IUbicacionService ubicacionService;

    @GetMapping("/normalizar")
    public ResponseEntity<UbicacionOutputDTO> obtenerUbicacion(@RequestParam Double latitud, @RequestParam Double longitud) {
        UbicacionOutputDTO ubicacion = ubicacionService.obtenerUbicacionOutPut(latitud, longitud);

        ResponseEntity<UbicacionOutputDTO> response = ResponseEntity.ok(ubicacion);

        System.out.println("Response enviado: status=" + response.getStatusCode() + ", body=" + response.getBody()); //LOG PARA VER LA RESPUESTA, SACARRR

        return response;
    }

}
