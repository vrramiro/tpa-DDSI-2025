package ar.utn.dssi.app_web.controllers;


import ar.utn.dssi.app_web.dto.Consenso.ConsensoDTO;
import ar.utn.dssi.app_web.dto.Consenso.TipoConsenso;
import ar.utn.dssi.app_web.dto.Fuente.TipoFuente;
import ar.utn.dssi.app_web.dto.input.ColeccionResponseDTO;
import ar.utn.dssi.app_web.dto.input.PageResponseDTO;
import ar.utn.dssi.app_web.dto.output.ColeccionRequestDTO;
import ar.utn.dssi.app_web.dto.output.HechoOutputDTO;
import ar.utn.dssi.app_web.services.Interfaces.ICategoriaService;
import ar.utn.dssi.app_web.services.Interfaces.IColeccionService;
import ar.utn.dssi.app_web.services.Interfaces.IHechoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping("/colecciones")
@RequiredArgsConstructor
public class ColeccionController {

  private final IColeccionService coleccionService;
  private final IHechoService hechosService;
  private final ICategoriaService categoriaService;

  @GetMapping
  public String listarColecciones(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "12") int size,
                                  @RequestParam(required = false) String filtro,
                                  @RequestParam(required = false, defaultValue = "titulo,asc") String sort,Model model) {

    PageResponseDTO<ColeccionResponseDTO> pageColeccionResponseDTO = coleccionService.listarColecciones(page,size,filtro,sort);

    model.addAttribute("colecciones", pageColeccionResponseDTO.getContent());
    model.addAttribute("page", page);
    model.addAttribute("size", size);
    model.addAttribute("sort", sort);
    model.addAttribute("filtro", filtro == null ? "" : filtro);
    model.addAttribute("totalPages", pageColeccionResponseDTO.getTotalPages());
    model.addAttribute("totalElements", pageColeccionResponseDTO.getTotalElements());
    model.addAttribute("isFirst", pageColeccionResponseDTO.getFirst());
    model.addAttribute("isLast", pageColeccionResponseDTO.getLast());
    model.addAttribute("titulo", "Colecciones");


    model.addAttribute("baseUrl", "/colecciones");

    return "colecciones/lista_colecciones";
  }

  @GetMapping("/{id}/hechos")
  public String listarHechosDeColeccion(@PathVariable("id") Long coleccionId,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "6") int size,
                                        @RequestParam(required = false) String filtro,
                                        @RequestParam(required = false, defaultValue = "fecha,desc") String sort,
                                        Model model) {

    PageResponseDTO<HechoOutputDTO> pageHechos = hechosService.listarHechosDeColeccion(coleccionId, page, size, filtro, sort);

    model.addAttribute("pageHechos", pageHechos);
    model.addAttribute("hechos", pageHechos.getContent()); // alias simple para iterar
    model.addAttribute("page", pageHechos.getNumber());
    model.addAttribute("size", pageHechos.getSize());
    model.addAttribute("sort", sort);
    model.addAttribute("filtro", filtro == null ? "" : filtro);
    model.addAttribute("totalPages", pageHechos.getTotalPages());
    model.addAttribute("totalElements", pageHechos.getTotalElements());
    model.addAttribute("isFirst", pageHechos.getFirst());
    model.addAttribute("isLast", pageHechos.getLast());
    model.addAttribute("titulo", "Hechos de la Colección");

    model.addAttribute("baseUrl", "/colecciones/" + coleccionId + "/hechos");


    return "hechos/listaHechosColeccion";
  }

  @GetMapping("/nuevo")
  public String mostrarFormularioCrear(Model model) {
    ColeccionRequestDTO coleccion = new ColeccionRequestDTO();
    coleccion.setConsenso(new ConsensoDTO());
    coleccion.setFuentes(new ArrayList<>());

    model.addAttribute("categorias", categoriaService.obtenerCategorias());
    model.addAttribute("coleccion", coleccion);
    model.addAttribute("tiposDeConsenso", TipoConsenso.values());
    model.addAttribute("tiposDeFuentes", TipoFuente.values());
    model.addAttribute("titulo", "Crear Colección");

    return "colecciones/crearColecciones";
  }

  @PostMapping("/crear")
  public String crearColeccion(@ModelAttribute("coleccion") ColeccionRequestDTO coelccionCreada, Model model, RedirectAttributes redirectAttributes, BindingResult binding){

    ColeccionResponseDTO coleccionCreada = coleccionService.crearColeccion(coelccionCreada);

    if (binding.hasErrors()) {
      redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.coleccion", binding);
      redirectAttributes.addFlashAttribute("coleccion", coelccionCreada);
      return "redirect:/colecciones/crear";
    }

    redirectAttributes.addFlashAttribute("mensaje", "Colección creada exitosamente");
    redirectAttributes.addFlashAttribute("tipoMensaje", "success");
    redirectAttributes.addFlashAttribute("coleccionCreada", coleccionCreada);

    System.out.println(coleccionCreada.getCriterios());
    return "redirect:/colecciones";
  }

  @GetMapping("/gestion_colecciones")
  public String gestionColeccion(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "12") int size,
                                 @RequestParam(required = false) String filtro,
                                 @RequestParam(required = false, defaultValue = "titulo,asc") String sort,Model model) {

    model.addAttribute("titulo", "Gestion de Colecciones");

    PageResponseDTO<ColeccionResponseDTO> pageColeccionResponseDTO = coleccionService.listarColecciones(page,size,filtro,sort);

    model.addAttribute("colecciones", pageColeccionResponseDTO.getContent());
    model.addAttribute("page", page);
    model.addAttribute("size", size);
    model.addAttribute("sort", sort);
    model.addAttribute("filtro", filtro == null ? "" : filtro);
    model.addAttribute("totalPages", pageColeccionResponseDTO.getTotalPages());
    model.addAttribute("totalElements", pageColeccionResponseDTO.getTotalElements());
    model.addAttribute("isFirst", pageColeccionResponseDTO.getFirst());
    model.addAttribute("isLast", pageColeccionResponseDTO.getLast());

    model.addAttribute("baseUrl", "/colecciones/gestion_colecciones");

    return "colecciones/gestionColeccionesAdmin";
  }

  @PostMapping("{id}/eliminar")
  public String eliminarColeccion(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
    coleccionService.eliminarColeccion(id);
    redirectAttributes.addFlashAttribute("mensaje", "Colección eliminada");
    redirectAttributes.addFlashAttribute("tipoMensaje", "success");
    return "redirect:/colecciones/gestion_colecciones";
  }

  @GetMapping("{id}/modificar")
  public String mostrarFormularioModificar(@PathVariable Long id, Model model) {
    ColeccionResponseDTO coleccion = coleccionService.obtenerColeccion(id);

    model.addAttribute("tiposDeConsenso", TipoConsenso.values());
    model.addAttribute("tiposDeFuentes", TipoFuente.values());
    model.addAttribute("categorias", categoriaService.obtenerCategorias());
    model.addAttribute("coleccion", coleccion);

    model.addAttribute("titulo", "Editar Colección");
    return "colecciones/modificarColecciones";
  }

  @PostMapping("{id}/actualizar")
  public String actualizarColeccion(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
    coleccionService.actualizarColeccion(id);
    redirectAttributes.addFlashAttribute("mensaje", "Colección Actualizada");
    redirectAttributes.addFlashAttribute("tipoMensaje", "success");
    return "redirect:/colecciones/gestion_colecciones";
  }

}
