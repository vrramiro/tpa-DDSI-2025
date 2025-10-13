package ar.utn.dssi.app_web.controllers;

import ar.utn.dssi.app_web.dto.EstadoHecho;
import ar.utn.dssi.app_web.dto.input.HechoInputDTO;
import ar.utn.dssi.app_web.dto.input.PageResponseDTO;
import ar.utn.dssi.app_web.dto.output.HechoOutputDTO;
import ar.utn.dssi.app_web.error.UbicacionInvalida;
import ar.utn.dssi.app_web.error.ValidationException;
import ar.utn.dssi.app_web.mappers.HechoMapper;
import ar.utn.dssi.app_web.services.CategoriaService;
import ar.utn.dssi.app_web.services.HechoServices;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/hechos")
@RequiredArgsConstructor
public class HechoController {

  private static final Logger log = LoggerFactory.getLogger(HechoController.class);
  private final HechoServices hechosService;
  private final CategoriaService categoriaService;

  @GetMapping("/nuevo")
  public String mostrarFormularioCrear(Model model) {
    model.addAttribute("titulo", "Crear Nuevo Hecho");
    model.addAttribute("hecho", new HechoInputDTO());
    model.addAttribute("categorias", categoriaService.obtenerCategorias());
    return "hechos/crearHecho";
  }

  @PostMapping("/crear")
  public String crearHecho(@ModelAttribute("hecho") HechoInputDTO hechoInputDTO,
                           BindingResult bindingResult,
                           Model model,
                           RedirectAttributes redirectAttributes) {

    try {
      boolean exitoso = hechosService.crearHecho(hechoInputDTO);
      if (exitoso) {
        model.addAttribute("mensaje", "El hecho fue subido correctamente, está siendo procesado.");
        model.addAttribute("tipoMensaje", "success");
        model.addAttribute("urlRedireccion", "/explorador");
      } else {
        model.addAttribute("mensaje", "Error al cargar el hecho, inténtelo nuevamente en un rato.");
        model.addAttribute("tipoMensaje", "error");
      }
      model.addAttribute("titulo", "Crear Nuevo Hecho");
      return "hechos/crearHecho";
    } catch (UbicacionInvalida e) {
      bindingResult.rejectValue("latitud", "ubicacionInvalida", "La ubicación debe estar dentro de Argentina");
      bindingResult.rejectValue("longitud", "ubicacionInvalida", "La ubicación debe estar dentro de Argentina");
      model.addAttribute("titulo", "Crear Nuevo Hecho");
      model.addAttribute("categorias", categoriaService.obtenerCategorias());
      return "hechos/crearHecho";
    } catch (ValidationException e) {
      convertirValidationExceptionABindingResult(e, bindingResult);
      model.addAttribute("titulo", "Crear Nuevo Hecho");
      return "hechos/crearHecho";
    } catch (Exception e) {
      log.error("Error al crear hecho", e);
      model.addAttribute("mensaje", "Error inesperado al crear el hecho: " + e.getMessage());
      model.addAttribute("tipoMensaje", "error");
      model.addAttribute("titulo", "Crear Nuevo Hecho");
      return "hechos/crearHecho";
    }
  }

  @GetMapping("/{id}")
  public String hechoUnico(@PathVariable Long id, Model model) {
    Optional<HechoOutputDTO> hechoOutputDTO = hechosService.obtenerHechoPorId(id);
    if (hechoOutputDTO.isEmpty()) {
      return "redirect:/404";
    }

    model.addAttribute("titulo", hechoOutputDTO.get().getTitulo());
    model.addAttribute("hecho", hechoOutputDTO.get());
    return "hechos/hecho";
  }

  @PostMapping("/{id}/aceptar")
  public String aceptarHecho(@PathVariable Long id, RedirectAttributes redirectAttributes) {
    try {
      hechosService.cambiarEstadoHecho(id, EstadoHecho.ACEPTADO);
      redirectAttributes.addFlashAttribute("mensaje", "Hecho aceptado correctamente");
      redirectAttributes.addFlashAttribute("tipo", "success");
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("mensaje", "Error al aceptar el hecho");
      redirectAttributes.addFlashAttribute("tipo", "error");
    }
    return "redirect:/hechos/" + id;
  }

  @PostMapping("/{id}/rechazar")
  public String rechazarHecho(@PathVariable Long id, RedirectAttributes redirectAttributes) {
    try {
      hechosService.cambiarEstadoHecho(id, EstadoHecho.RECHAZADO);
      redirectAttributes.addFlashAttribute("mensaje", "Hecho rechazado correctamente");
      redirectAttributes.addFlashAttribute("tipo", "success");
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("mensaje", "Error al rechazar el hecho");
      redirectAttributes.addFlashAttribute("tipo", "error");
    }
    return "redirect:/hechos/" + id;
  }

  @GetMapping("/{id}/editar")
  public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
    Optional<HechoOutputDTO> hechoOutputOpt = hechosService.obtenerHechoPorId(id);

    if (hechoOutputOpt.isEmpty()) {
      return "redirect:/404";
    }

    HechoOutputDTO hechoOutput = hechoOutputOpt.get();
    HechoInputDTO hechoInput = HechoMapper.outputToInput(hechoOutput);

    model.addAttribute("hecho", hechoInput);
    model.addAttribute("hechoOutput", hechoOutput);
    model.addAttribute("titulo", "Editar Hecho");
    model.addAttribute("categorias", categoriaService.obtenerCategorias());

    return "hechos/editarHecho";
  }

  @PostMapping("/{id}/editar")
  public String editarHecho(@PathVariable Long id,
                            @ModelAttribute("hecho") HechoInputDTO hechoInputDTO,
                            BindingResult bindingResult,
                            Model model,
                            RedirectAttributes redirectAttributes) {
    try {
      boolean exitoso = hechosService.editarHecho(id, hechoInputDTO);
      if (exitoso) {
        model.addAttribute("mensaje", "Hecho editado correctamente");
        model.addAttribute("tipoMensaje", "success");
        model.addAttribute("titulo", "Editar Hecho");
        model.addAttribute("hechoOutput", hechosService.obtenerHechoPorId(id));
        model.addAttribute("hecho", hechoInputDTO);
        model.addAttribute("categorias", categoriaService.obtenerCategorias());
        return "/hechos/editarHecho";
      } else {
        model.addAttribute("mensaje", "Error al editar el hecho, inténtelo nuevamente.");
        model.addAttribute("tipoMensaje", "error");
        model.addAttribute("titulo", "Editar Hecho");
        model.addAttribute("hecho", hechoInputDTO);
        model.addAttribute("hechoOutput", hechosService.obtenerHechoPorId(id).orElse(null));
        model.addAttribute("categorias", categoriaService.obtenerCategorias());
        return "/hechos/editarHecho";
      }
    } catch (UbicacionInvalida e) {
      bindingResult.rejectValue("latitud", "ubicacionInvalida", "La ubicación debe estar dentro de Argentina");
      bindingResult.rejectValue("longitud", "ubicacionInvalida", "La ubicación debe estar dentro de Argentina");
      model.addAttribute("titulo", "Editar Hecho");
      model.addAttribute("hecho", hechoInputDTO);
      model.addAttribute("hechoOutput", hechosService.obtenerHechoPorId(id).orElse(null));
      model.addAttribute("categorias", categoriaService.obtenerCategorias());
      return "/hechos/editarHecho";
    } catch (ValidationException e) {
      convertirValidationExceptionABindingResult(e, bindingResult);
      model.addAttribute("titulo", "Editar Hecho");
      model.addAttribute("hecho", hechoInputDTO);
      model.addAttribute("hechoOutput", hechosService.obtenerHechoPorId(id).orElse(null));
      model.addAttribute("categorias", categoriaService.obtenerCategorias());
      return "/hechos/editarHecho";
    } catch (Exception e) {
      log.error("Error al editar hecho", e);
      model.addAttribute("mensaje", "Error inesperado al editar el hecho: " + e.getMessage());
      model.addAttribute("tipoMensaje", "error");
      model.addAttribute("titulo", "Editar Hecho");
      model.addAttribute("hecho", hechoInputDTO);
      model.addAttribute("hechoOutput", hechosService.obtenerHechoPorId(id).orElse(null));
      model.addAttribute("categorias", categoriaService.obtenerCategorias());
      return "/hechos/editarHecho";
    }
  }

  @GetMapping("/mis_hechos")
  public String listarMisHechos(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "9") int size,
                                @RequestParam(required = false) String filtro,
                                @RequestParam(required = false, defaultValue = "titulo,asc") String sort,
                                Model model) {

    PageResponseDTO<HechoOutputDTO> pageResponseDTO = hechosService.listarHechos(page, size, filtro, sort);

    model.addAttribute("hechos", pageResponseDTO.getContent());
    model.addAttribute("page", page);
    model.addAttribute("size", size);
    model.addAttribute("sort", sort);
    model.addAttribute("filtro", filtro == null ? "" : filtro);
    model.addAttribute("totalPages", pageResponseDTO.getTotalPages());
    model.addAttribute("totalElements", pageResponseDTO.getTotalElements());
    model.addAttribute("isFirst", pageResponseDTO.getFirst());
    model.addAttribute("isLast", pageResponseDTO.getLast());
    model.addAttribute("titulo", "Mis Hechos");

    model.addAttribute("baseUrl", "/hechos/mis_hechos");

    return "hechos/misHechos";
  }

  @GetMapping("/gestion_hecho")
  public String gestionHecho(@RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "10") int size,
                             @RequestParam(required = false) String estado,
                             @RequestParam(required = false, defaultValue = "fechaCarga,desc") String sort,
                             Model model) {

    EstadoHecho estadoEnum = null;
    if (estado != null && !estado.trim().isEmpty()) {
      try {
        estadoEnum = EstadoHecho.valueOf(estado.toUpperCase());
      } catch (IllegalArgumentException e) {
        estadoEnum = null;
      }
    }

    String filtroEstado = Optional.ofNullable(estadoEnum)
            .map(Enum::name)
            .orElse(null);

    PageResponseDTO<HechoOutputDTO> pageResponseDTO = hechosService.listarHechos(page, size, filtroEstado, sort);

    model.addAttribute("hechos", pageResponseDTO.getContent());
    model.addAttribute("page", page);
    model.addAttribute("size", size);
    model.addAttribute("sort", sort);
    model.addAttribute("estado", estado == null ? "" : estado);
    model.addAttribute("totalPages", pageResponseDTO.getTotalPages());
    model.addAttribute("totalElements", pageResponseDTO.getTotalElements());
    model.addAttribute("isFirst", pageResponseDTO.getFirst());
    model.addAttribute("isLast", pageResponseDTO.getLast());
    model.addAttribute("titulo", "Gestión de Hechos");
    model.addAttribute("baseUrl", "/hechos/gestion_hecho");

    return "hechos/gestionHechosAdmin";
  }

  private void convertirValidationExceptionABindingResult(ValidationException e, BindingResult bindingResult) {
    if(e.hasFieldErrors()) {
      e.getFieldErrors().forEach((field, error) -> bindingResult.rejectValue(field, "error." + field, error));
    }
  }

/***********************************************************************************************************************/
/***************************************************LO DE ABAJO FALTA***************************************************/
/***********************************************************************************************************************/


  //TODO VER QUE ES ESTO
  @GetMapping("/detalle_hech_admin")
  public String detalleHechoAdmin() {
    return "hechos/detalleHechoAdmin";
  }

  //TODO VERLO CON SANTI
  @GetMapping("/lista_hechos_coleccion")
  public String hechosCoelccion(Model model) {
    model.addAttribute("titulo", "Hechos de Coleccion");
    return "hechos/listaHechosColeccion";
  }
}

