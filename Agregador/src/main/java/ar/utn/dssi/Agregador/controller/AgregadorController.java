package ar.utn.dssi.Agregador.controller;

import ar.utn.dssi.Agregador.models.DTOs.outputDTO.HechoOutputDTO;
import ar.utn.dssi.Agregador.services.IHechosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/hechos")
public class AgregadorController {
  @Autowired
  private IHechosService hechosService;

  @PutMapping("/actualizar")
  public void actualizarHechos(){
    this.hechosService.actualizarHechos();
  }

  @GetMapping("/hechos")
  public List<HechoOutputDTO> obtenerHechos(){
    return this.hechosService.obtenerHechos();
  }

}




