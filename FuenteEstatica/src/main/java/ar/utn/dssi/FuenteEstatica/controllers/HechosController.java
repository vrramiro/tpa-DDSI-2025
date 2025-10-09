package ar.utn.dssi.FuenteEstatica.controllers;


import ar.utn.dssi.FuenteEstatica.models.DTOs.output.HechoOutputDTO;
import ar.utn.dssi.FuenteEstatica.services.IHechoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/hechos")

public class HechosController {
    @Autowired
    private IHechoServicio hechoServicio;

    @PostMapping("/importar")
    public ResponseEntity<Void> importarArchivo(@RequestParam("archivo") MultipartFile archivo) {

        try {
            File tempFile = File.createTempFile("importado-", archivo.getOriginalFilename());
            archivo.transferTo(tempFile);

            this.hechoServicio.importarArchivo(tempFile);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/nuevos")
    public ResponseEntity<List<HechoOutputDTO>> obtenerHechos
            (@RequestParam(name = "fechaDesde", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaDesde) {
        List<HechoOutputDTO> hechos = hechoServicio.obtenerHechos(fechaDesde);
        return ResponseEntity.ok(hechos);
    }
}
