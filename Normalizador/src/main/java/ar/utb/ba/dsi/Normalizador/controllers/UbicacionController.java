package ar.utb.ba.dsi.Normalizador.controllers;

import ar.utb.ba.dsi.Normalizador.dto.output.ProvinciaOutputDTO;
import ar.utb.ba.dsi.Normalizador.dto.output.UbicacionOutputDTO;
import ar.utb.ba.dsi.Normalizador.service.IUbicacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController
@RequestMapping("/ubicacion")
@RequiredArgsConstructor
public class UbicacionController {

  private final IUbicacionService ubicacionService;

  @GetMapping("/normalizar")
  public ResponseEntity<UbicacionOutputDTO> obtenerUbicacion(@RequestParam Double latitud, @RequestParam Double longitud) {
    UbicacionOutputDTO ubicacion = ubicacionService.obtenerUbicacionOutPut(latitud, longitud);

    ResponseEntity<UbicacionOutputDTO> response = ResponseEntity.ok(ubicacion);

    System.out.println("Response enviado: status=" + response.getStatusCode() + ", body=" + response.getBody()); //LOG PARA VER LA RESPUESTA, SACARRR

    return response;
  }

  @GetMapping("/provincias")
  public ResponseEntity<List<ProvinciaOutputDTO>> listarProvincias() {
    List<ProvinciaOutputDTO> provincias = ubicacionService.obtenerProvincias();
    return ResponseEntity.ok(provincias);
  }
}
