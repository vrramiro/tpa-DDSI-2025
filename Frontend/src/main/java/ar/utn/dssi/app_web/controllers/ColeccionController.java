package ar.utn.dssi.app_web.controllers;

import ar.utn.dssi.app_web.dto.Consenso.ConsensoDTO;
import ar.utn.dssi.app_web.dto.Consenso.TipoConsenso;
import ar.utn.dssi.app_web.dto.Fuente.TipoFuente;
import ar.utn.dssi.app_web.dto.input.ColeccionResponseDTO;
import ar.utn.dssi.app_web.dto.input.PageResponseDTO;
import ar.utn.dssi.app_web.dto.output.ColeccionRequestDTO;
import ar.utn.dssi.app_web.dto.output.HechoOutputDTO;

import ar.utn.dssi.app_web.error.NotFoundException; // Se mantiene por si se usa en otros métodos
import ar.utn.dssi.app_web.services.Interfaces.ICategoriaService;
import ar.utn.dssi.app_web.services.Interfaces.IColeccionService;
import ar.utn.dssi.app_web.services.Interfaces.IHechoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping("/colecciones")
public class ColeccionController {
  private static final Logger log = LoggerFactory.getLogger(ColeccionController.class);
  private final IColeccionService coleccionService;
  private final IHechoService hechosService;
  private final ICategoriaService categoriaService;

  public ColeccionController(IColeccionService coleccionService, IHechoService hechosService, ICategoriaService categoriaService) {
    this.coleccionService = coleccionService;
    this.hechosService = hechosService;
    this.categoriaService = categoriaService;
  }

  @GetMapping
  public String listarColecciones(@RequestParam(defaultValue = "0") Integer page,
                                  Model model) {
    PageResponseDTO<ColeccionResponseDTO> pageColeccion;

    try {
      pageColeccion = coleccionService.listarColecciones(page);

      if(pageColeccion == null) {
        log.warn("El servicio devolvió null para colecciones. Inicializando DTO vacío.");
        pageColeccion = new PageResponseDTO<>(); // El service ya debería prevenir el null, pero esto es un fallback seguro
      }

      // **Lógica Corregida: Se elimina el bloque if(pageColeccion.getContent().isEmpty()){...}**
      // Se permite que la lista vacía fluya al modelo para ser manejada en el template.

      model.addAttribute("colecciones", pageColeccion.getContent());
      model.addAttribute("page", pageColeccion.getNumber());
      model.addAttribute("size", pageColeccion.getSize());
      model.addAttribute("totalPages", pageColeccion.getTotalPages());
      model.addAttribute("totalElements", pageColeccion.getTotalElements());
      model.addAttribute("isFirst", pageColeccion.isFirst());
      model.addAttribute("isLast", pageColeccion.isLast());
      model.addAttribute("titulo", "Colecciones");
      model.addAttribute("baseUrl", "/colecciones");

      return "colecciones/lista_colecciones";

    } catch (Exception ex) {
      log.error("Error al listar colecciones", ex);
      model.addAttribute("error", "No se pudieron obtener las colecciones. Intente más tarde.");
      return "redirect:/404";
    }
  }

  @GetMapping("/{handle}/hechos")
  public String listarHechosDeColeccion(@PathVariable("handle") String handle, Model model) {

    PageResponseDTO<HechoOutputDTO> pageHechos = hechosService.listarHechosDeColeccion(handle);

    model.addAttribute("pageHechos", pageHechos);
    model.addAttribute("hechos", pageHechos.getContent());
    model.addAttribute("page", pageHechos.getNumber());
    model.addAttribute("size", pageHechos.getSize());
    model.addAttribute("totalPages", pageHechos.getTotalPages());
    model.addAttribute("totalElements", pageHechos.getTotalElements());
    model.addAttribute("titulo", "Hechos de la Colección");

    model.addAttribute("baseUrl", "/colecciones/" + handle + "/hechos");

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
  public String crearColeccion(@ModelAttribute("coleccion") ColeccionRequestDTO coelccionCreada, RedirectAttributes redirectAttributes, BindingResult binding){

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
  public String gestionColeccion(@RequestParam(defaultValue = "0") Integer page,
                                 @RequestParam(defaultValue = "12") Integer size,
                                 @RequestParam(required = false) String filtro,
                                 @RequestParam(required = false, defaultValue = "titulo,asc") String sort,
                                 Model model) {

    model.addAttribute("titulo", "Gestion de Colecciones");

    PageResponseDTO<ColeccionResponseDTO> pageColeccionResponseDTO = coleccionService.listarColecciones(page);

    model.addAttribute("colecciones", pageColeccionResponseDTO.getContent());
    model.addAttribute("page", page);
    model.addAttribute("size", size);
    model.addAttribute("sort", sort);
    model.addAttribute("filtro", filtro == null ? "" : filtro);
    model.addAttribute("totalPages", pageColeccionResponseDTO.getTotalPages());
    model.addAttribute("totalElements", pageColeccionResponseDTO.getTotalElements());

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
    Optional<ColeccionResponseDTO> coleccion = coleccionService.obtenerColeccion(id);

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