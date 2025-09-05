package ar.utb.ba.dsi.Normalizador.controllers;

import ar.utb.ba.dsi.Normalizador.models.DTOs.UbicacionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/solicitudes")
public class UbicacionController {


    @GetMapping("/ubicacion")
    public ResponseEntity<UbicacionResponse> obtenerHechos(Double latitud, Double longitud) {
        //LOGICAAAA
        return null;
    }
}
