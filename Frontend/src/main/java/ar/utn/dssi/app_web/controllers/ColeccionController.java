package ar.utn.dssi.app_web.controllers;

import ar.utn.dssi.app_web.dto.ColeccionDTO;
import ar.utn.dssi.app_web.services.ColeccionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/colecciones")
@RequiredArgsConstructor
public class ColeccionController {
  private final ColeccionService coleccionesService;

  @GetMapping
  public String listarColecciones(Model model) {
    List<ColeccionDTO> colecciones = coleccionesService.obtenerTodasLasColecciones();
    model.addAttribute("colecciones", colecciones);
    model.addAttribute("titulo", "Colecciones");
    return "colecciones/lista_colecciones";
  }

  // Controllers para verificar funcionamiento de la vista (Borrar si no son necesarias)
  @GetMapping("/nuevo")
  public String mostrarFormularioCrear(Model model) {
    model.addAttribute("coleccion", new ColeccionDTO());
    model.addAttribute("titulo", "Crear Coleccion");
    return "colecciones/crearColecciones";
  }

  @GetMapping("/gestion_colecciones")
  public String gestionHecho(Model model) {
    model.addAttribute("titulo", "Gestion de Colecciones");
    return "colecciones/gestionColeccionesAdmin";
  }

  @GetMapping("/modificar")
  public String hechoUnico(Model model) {
    model.addAttribute("titulo", "Editar Coleccion");
    return "colecciones/modificarColecciones";
  }
}
