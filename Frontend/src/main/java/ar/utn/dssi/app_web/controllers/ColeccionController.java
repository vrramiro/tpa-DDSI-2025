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
  public String listarColecciones(Model model) {
    PageResponseDTO<ColeccionResponseDTO> pageColeccion = coleccionService.listarColecciones();

    model.addAttribute("pageHechos", pageColeccion);
    model.addAttribute("hechos", pageColeccion.getContent());
    model.addAttribute("page", pageColeccion.getNumber());
    model.addAttribute("size", pageColeccion.getSize());
    model.addAttribute("totalPages", pageColeccion.getTotalPages());
    model.addAttribute("totalElements", pageColeccion.getTotalElements());
    model.addAttribute("isFirst", pageColeccion.getFirst());
    model.addAttribute("isLast", pageColeccion.getLast());
    model.addAttribute("titulo", "Colecciones");
    model.addAttribute("baseUrl", "/colecciones");

    return "colecciones/lista_colecciones";
  }

  @GetMapping("/{id}/hechos")
  public String listarHechosDeColeccion(@PathVariable("id") Long coleccionId, Model model) {

    PageResponseDTO<HechoOutputDTO> pageHechos = hechosService.listarHechosDeColeccion(coleccionId);

    model.addAttribute("pageHechos", pageHechos);
    model.addAttribute("hechos", pageHechos.getContent());
    model.addAttribute("page", pageHechos.getNumber());
    model.addAttribute("size", pageHechos.getSize());
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
  public String gestionColeccion(@RequestParam(defaultValue = "0") Integer page,
                                 @RequestParam(defaultValue = "12") Integer size,
                                 @RequestParam(required = false) String filtro,
                                 @RequestParam(required = false, defaultValue = "titulo,asc") String sort,Model model) {

    model.addAttribute("titulo", "Gestion de Colecciones");

    PageResponseDTO<ColeccionResponseDTO> pageColeccionResponseDTO = coleccionService.listarColecciones();

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

  private <T> void addPageAttrs(
          Model model,
          PageResponseDTO<T> pageDto,
          String titulo,
          String baseUrl,
          String listAlias
  ) {
    if (pageDto == null) {
      pageDto = (PageResponseDTO<T>) emptyPage(); // fallback seguro
    }

    model.addAttribute(listAlias, pageDto.getContent() == null ? java.util.List.of() : pageDto.getContent());

    model.addAttribute("pageDto", pageDto);

    model.addAttribute("page", n(pageDto.getNumber()));
    model.addAttribute("size", n(pageDto.getSize()));
    model.addAttribute("totalPages", n(pageDto.getTotalPages()));
    model.addAttribute("totalElements", nl(pageDto.getTotalElements()));
    model.addAttribute("isFirst", nb(pageDto.getFirst()));
    model.addAttribute("isLast", nb(pageDto.getLast()));
    model.addAttribute("titulo", titulo);
    model.addAttribute("baseUrl", baseUrl);
  }


  private PageResponseDTO<?> emptyPage() {
    PageResponseDTO<Object> p = new PageResponseDTO<>();
    p.setContent(java.util.List.of());
    p.setNumber(0);
    p.setSize(0);
    p.setTotalElements(0L);
    p.setTotalPages(0);
    p.setFirst(true);
    p.setLast(true);
    return p;
  }
  private int n(Integer v) { return v == null ? 0 : v; }
  private long nl(Long v) { return v == null ? 0L : v; }
  private boolean nb(Boolean v) { return v != null && v; }
}


