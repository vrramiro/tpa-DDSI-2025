package ar.utn.dssi.FuenteDinamica.controllers;

import ar.utn.dssi.FuenteDinamica.models.DTOs.outputs.HechoOutputDTO;
import ar.utn.dssi.FuenteDinamica.models.entities.Hecho;
import ar.utn.dssi.FuenteDinamica.services.IHechosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/hechos")

public class HechosController {
  @Autowired
  private IHechosService hechosService;

  @PostMapping("/crear")
  public HechoOutputDTO crearHecho(@RequestBody Hecho hecho) {
    //TODO: desarrollar
    return null;
  }

  @GetMapping
  public List<HechoOutputDTO> obtenerHechos() {
    return this.hechosService.obtenerHechos();
  }
}
