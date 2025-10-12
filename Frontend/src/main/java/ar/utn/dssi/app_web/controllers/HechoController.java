package ar.utn.dssi.app_web.controllers;

import ar.utn.dssi.app_web.dto.input.HechoInputDTO;
import ar.utn.dssi.app_web.dto.output.HechoOutputDTO;
import ar.utn.dssi.app_web.error.ValidationException;
import ar.utn.dssi.app_web.services.HechoServices;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/hechos")
@RequiredArgsConstructor
public class HechoController {
  private static final Logger log = LoggerFactory.getLogger(HechoController.class);
  private final HechoServices hechosService;

  @GetMapping("/nuevo")
  @PreAuthorize("hasAnyRole('CONTRIBUYENTE', 'ADMINISTRADOR')")
  public String mostrarFormularioCrear(Model model) {
    model.addAttribute("titulo", "Crear Nuevo Hecho");
    return "hechos/crearHecho";
  }

  @PostMapping("/crear")
  @PreAuthorize("hasAnyRole('CONTRIBUYENTE', 'ADMINISTRADOR')")
  public String crearHecho(@ModelAttribute("hecho") HechoInputDTO hechoInputDTO,
                           BindingResult bindingResult,
                           Model model,
                           RedirectAttributes redirectAttributes) {

    try {
      HechoOutputDTO hechoCreado = hechosService.crearHecho(hechoInputDTO);
      redirectAttributes.addFlashAttribute("mensaje", "Hecho creado exitosamente");
      redirectAttributes.addFlashAttribute("tipoMensaje", "success");
      return "redirect:/explorador/" + hechoCreado;
    } catch (ValidationException e) {
      convertirValidationExceptionABindingResult(e, bindingResult);
      model.addAttribute("titulo", "Crear Nuevo Hecho");
      return "redirect:/crear";
    } catch (Exception e) {
      log.error("Error al crear hecho", e);
      model.addAttribute("error", "Error al crear el hecho: " + e.getMessage());
      model.addAttribute("titulo", "Crear Nuevo Hecho");
      return "redirect:/crear";
    }
  }

  @GetMapping("/mis_hechos")
  @PreAuthorize("hasAnyRole('CONTRIBUYENTE', 'ADMINISTRADOR')")
  public String listarMisHechos(Model model) {
    //List<HechoDTO> hechos = hechosService.obtenerHechosDeUsuario(idUsuario).get();
    //model.addAttribute("hechos", hechos);
    model.addAttribute("titulo", "Mis Hechos");
    return "hechos/misHechos";
  }

  //TODO VER QUE ES ESTO
  @GetMapping("/detalle_hech_admin")
  @PreAuthorize("hasAnyRole('ADMINISTRADOR')")
  public String detalleHechoAdmin() {
    return "hechos/detalleHechoAdmin";
  }

  @GetMapping("/gestion_hecho")
  @PreAuthorize("hasAnyRole('ADMINISTRADOR')")
  public String gestionHecho(Model model) {
    model.addAttribute("titulo", "Gestion de Hechos");
    return "hechos/gestionHechosAdmin";
  }

  @GetMapping("/hecho_unico")
  public String hechoUnico(Model model) {
    //En titulo estaria bueno que este el nombre del hecho
    model.addAttribute("titulo", "Hecho");
    return "hechos/hecho";
  }

  @GetMapping("/lista_hechos_coleccion")
  @PreAuthorize("hasAnyRole('CONTRIBUYENTE', 'ADMINISTRADOR')")
  public String hechosCoelccion(Model model) {
    model.addAttribute("titulo", "Hechos de Coleccion");
    return "hechos/listaHechosColeccion";
  }

  @GetMapping("/editar")
  @PreAuthorize("hasAnyRole('CONTRIBUYENTE', 'ADMINISTRADOR')")
  public String mostrarFormularioEdicion(Model model) {
    model.addAttribute("titulo", "Editar Hecho");
    return "hechos/editarHecho";
  }

  private void convertirValidationExceptionABindingResult(ValidationException e, BindingResult bindingResult) {
    if (e.hasFieldErrors()) {
      e.getFieldErrors().forEach((field, error) -> bindingResult.rejectValue(field, "error." + field, error));
    }
  }
}
