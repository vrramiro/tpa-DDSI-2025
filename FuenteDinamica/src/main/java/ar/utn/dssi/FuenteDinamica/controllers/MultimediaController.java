package ar.utn.dssi.FuenteDinamica.controllers;

import ar.utn.dssi.FuenteDinamica.models.entities.almacenadorMultimedia.AlmacenadorMultimedia;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;

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

    @GetMapping("/uploads/{filename:.+}")
    public ResponseEntity<Resource> servirArchivo(@PathVariable String filename) {
        try {
            Resource archivo = almacenadorMultimedia.cargarArchivo(filename);

            String contentType = Files.probeContentType(archivo.getFile().toPath());
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, contentType)
                    .body(archivo);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}