package ar.utn.dssi.FuenteDinamica.controllers;

import ar.utn.dssi.FuenteDinamica.models.entities.almacenadorMultimedia.AlmacenadorMultimedia;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/multimedia")
@RequiredArgsConstructor
@CrossOrigin(origins = "${front.base-url}")
public class MultimediaController {

    private final AlmacenadorMultimedia almacenadorMultimedia;

    @PostMapping("/upload")
    public ResponseEntity<String> subirArchivo(@RequestParam("file") MultipartFile file) {
        String ruta = almacenadorMultimedia.guardarArchivo(file);
        return ResponseEntity.ok(ruta);
    }
}