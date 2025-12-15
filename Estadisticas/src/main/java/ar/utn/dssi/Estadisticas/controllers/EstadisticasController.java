package ar.utn.dssi.Estadisticas.controllers;


import ar.utn.dssi.Estadisticas.dto.output.EstadisticaOutputDTO;
import ar.utn.dssi.Estadisticas.models.entities.TipoArchivo;
import ar.utn.dssi.Estadisticas.services.IEstadisticasService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
  public ResponseEntity<File> getArchivoEstadisticas(TipoArchivo tipo) {
    File archivo = estadisticasService.getArchivoEstadisticas(tipo);
    return ResponseEntity.ok(archivo);
  }
}

