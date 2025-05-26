package ar.utn.dssi.FuenteProxy.controller;

import ar.utn.dssi.FuenteProxy.models.DTOs.output.HechoOutputDTO;
import ar.utn.dssi.FuenteProxy.service.IHechosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class HechosController {
  @Autowired
  public IHechosService hechosService;

  @GetMapping("/api/hechos")
  public List<HechoOutputDTO> obtenerHechos() {
    return this.hechosService.obtenerHechos();
  }

  //TODO: Revisar otras operaciones
}
