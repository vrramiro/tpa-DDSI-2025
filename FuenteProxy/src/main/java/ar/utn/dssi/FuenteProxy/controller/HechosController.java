package ar.utn.dssi.FuenteProxy.controller;

import ar.utn.dssi.FuenteProxy.dto.output.HechoOutputDTO;
import ar.utn.dssi.FuenteProxy.service.IHechosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/hechos")
@RequiredArgsConstructor
public class HechosController {

  public final IHechosService hechosService;

  @GetMapping("/nuevos")
  public ResponseEntity<List<HechoOutputDTO>> obtenerHechos(@RequestParam LocalDateTime fechaUltimaComunicacion) {
    try {
      List<HechoOutputDTO> hechos = this.hechosService.obtenerHechos(fechaUltimaComunicacion);
      return ResponseEntity.ok(hechos);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  // Endpoint de prueba, luego se puede eliminar
  @PostMapping("/importar")
  public ResponseEntity<Void> importarHechos() {
    try {
      this.hechosService.importarHechos();
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      return ResponseEntity.status(500).build();
    }
  }

  //TODO: Revisar otras operaciones
  @PostMapping("/eliminar/{idHecho}")
  public ResponseEntity<Void> eliminarHecho(@PathVariable("idHecho") Long idHecho) {
    try {
      this.hechosService.eliminarHecho(idHecho);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      return ResponseEntity.notFound().build();
    }
  }

  //TODO:
  //Traer hechos metamapa
  //Traer colecciones
  //Traer hechos de colecciones
  //Solicitud de eliminacion meiante metodo eliminar hecho => agregar id de fuente dentro del json para que pueda identificar si es metamapa o no
  //(la solicitud semandaria por el body si es que se acepta la eliminacion dentro de ESTE sistema)
}
