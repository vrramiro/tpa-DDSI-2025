package ar.utn.dssi.Estadisticas.controllers;


import ar.utn.dssi.Estadisticas.dto.output.EstadisticaOutputDTO;
import ar.utn.dssi.Estadisticas.models.entities.TipoArchivo;
import ar.utn.dssi.Estadisticas.services.IEstadisticasService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


import java.io.File;

@RestController
@RequestMapping("/estadisticas")
@RequiredArgsConstructor
public class EstadisticasController {
  private final IEstadisticasService estadisticasService;

  @GetMapping("/coleccion/{idColeccion}/provincia")
  public ResponseEntity<EstadisticaOutputDTO> getProvinciasConMasHechosColecion(@PathVariable("idColeccion") String handle) {
    EstadisticaOutputDTO estadisticas = estadisticasService.getProvinciasConMasHechosColeccion(handle);
    return ResponseEntity.ok(estadisticas);
  }

  @GetMapping("/categoria")
  public ResponseEntity<EstadisticaOutputDTO> getCategoriaConMasHechos() {
    EstadisticaOutputDTO estadisticas = estadisticasService.getCategoriaConMasHechos();
    return ResponseEntity.ok(estadisticas);
  }

  @GetMapping("/categoria/{idCategoria}/provincia")
  public ResponseEntity<EstadisticaOutputDTO> getProvinciasConMasHechoCategoria(@PathVariable("idCategoria") Long idCategoria) {
    EstadisticaOutputDTO estadisticas = estadisticasService.getProvinciasConMasHechoCategoria(idCategoria);
    return ResponseEntity.ok(estadisticas);
  }

  @GetMapping("/categoria/{idCategoria}/horas")
  public ResponseEntity<EstadisticaOutputDTO> getHorasConMasHechosCategoria(@PathVariable("idCategoria") Long idCategoria) {
    EstadisticaOutputDTO estadisticas = estadisticasService.getHorasConMasHechosCategoria(idCategoria);
    return ResponseEntity.ok(estadisticas);
  }

  @GetMapping("/spam")
  public ResponseEntity<EstadisticaOutputDTO> getCantidadSpam() {
    EstadisticaOutputDTO estadisticas = estadisticasService.getCantidadSpam();
    return ResponseEntity.ok(estadisticas);
  }

  @GetMapping("/exportar")
  public ResponseEntity<Resource> getArchivoEstadisticas(@RequestParam(defaultValue = "CSV") TipoArchivo tipo) {
    File archivo = estadisticasService.getArchivoEstadisticas(tipo);

    if (archivo == null || !archivo.exists()) {
      return ResponseEntity.notFound().build();
    }

    Resource resource = new FileSystemResource(archivo);

    return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Estadisticas MetaMapa.csv")
            .contentType(MediaType.parseMediaType("text/csv"))
            .body(resource);
  }
}

