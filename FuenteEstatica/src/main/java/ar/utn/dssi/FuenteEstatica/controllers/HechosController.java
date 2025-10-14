package ar.utn.dssi.FuenteEstatica.controllers;


import ar.utn.dssi.FuenteEstatica.dto.output.HechoOutputDTO;
import ar.utn.dssi.FuenteEstatica.services.IHechoServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/hechos")
@RequiredArgsConstructor
public class HechosController {

  private final IHechoServicio hechoServicio;

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
