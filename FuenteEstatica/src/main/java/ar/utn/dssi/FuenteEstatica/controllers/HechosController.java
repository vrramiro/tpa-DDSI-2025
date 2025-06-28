package ar.utn.dssi.FuenteEstatica.controllers;


import ar.utn.dssi.FuenteEstatica.models.DTOs.output.HechoOutputDTO;
import ar.utn.dssi.FuenteEstatica.services.IHechoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/fuente/hechos")

public class HechosController {
    @Autowired
    private IHechoServicio hechoServicio;

    @PostMapping("/importar")
    public ResponseEntity<Void> importarArchivo(@RequestParam File archivo) {
        this.hechoServicio.importarArchivo(archivo);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/hechos")
    public ResponseEntity<List<HechoOutputDTO>> obtenerHechos() {
        List<HechoOutputDTO> hechos = hechoServicio.obtenerHechos();
        return ResponseEntity.ok(hechos);
        //TODO implementar endpoin para filtrado de hechos enviados
    }
}
