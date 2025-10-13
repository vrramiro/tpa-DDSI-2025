package ar.utn.dssi.app_web.controllers;

import ar.utn.dssi.app_web.dto.input.HechoInputDTO;
import ar.utn.dssi.app_web.dto.output.HechoOutputDTO;
import ar.utn.dssi.app_web.dto.SolicitudDTO;
import ar.utn.dssi.app_web.error.ValidationException;
import ar.utn.dssi.app_web.services.SolicitudService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/solicitudes")
@RequiredArgsConstructor
public class SolicitudController {

  private final SolicitudService solicitudService;
  private static final Logger log = LoggerFactory.getLogger(SolicitudController.class);

  @GetMapping("/gestion")
  public String gestionSolicitudes(
          @RequestParam(name = "estado", required = false, defaultValue = "Todos") String estado,
          Model model) {

    List<SolicitudDTO> solicitudes;
    if (estado.equalsIgnoreCase("Todos")) {
      solicitudes = solicitudService.obtenerTodasLasSolicitudes();
    } else {
      solicitudes = solicitudService.obtenerSolicitudesPorEstado(estado);
    }

    model.addAttribute("titulo", "Gestión de Solicitudes");
    model.addAttribute("solicitudes", solicitudes);
    model.addAttribute("estadoSeleccionado", estado);
    return "solicitudes/gestionSolicitudesAdmin";
  }

  @GetMapping("/panel")
  public String panelSolicitudes( @RequestParam(name = "id") Long solicitudId,
                                  Model model) {
    SolicitudDTO solicitud = solicitudService.obtenerSolicitudPorId(solicitudId);

    HechoOutputDTO hecho = solicitudService.obtenerHechoPorSolicitud(solicitudId);

    model.addAttribute("titulo", "Panel de Solicitudes");
    model.addAttribute("solicitud", solicitud);
    model.addAttribute("hecho", hecho);
    model.addAttribute("titulo", "Panel de Solicitudes");
    return "solicitudes/panelSolicitudAdmin";
  }

  @PostMapping("/actualizarEstado")
  public String actualizarEstadoSolicitud(@RequestParam Long solicitudId,
                                          @RequestParam String estado,
                                          RedirectAttributes redirectAttributes) {
    try {
      solicitudService.actualizarEstado(solicitudId, estado);
      redirectAttributes.addFlashAttribute("mensaje", "Estado actualizado correctamente");
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("error", "Error al actualizar el estado");
    }
    return "redirect:/solicitudes/panel?id=" + solicitudId;
  }


  @GetMapping("/crearSolicitud")
  public String mostrarFormularioCrear(Model model) {
    model.addAttribute("titulo", "Crear Nueva Solicitud");
    model.addAttribute("solicitud", new SolicitudDTO());
    return "solicitudes/solicitudEliminacion";
  }

  @GetMapping("/nueva")
  public String nuevaSolicitud(@ModelAttribute("solicitud") SolicitudDTO solicitudOutputDTO,
                               BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
    try {
      SolicitudDTO solicitudCreada = solicitudService.crearSolicitud(solicitudOutputDTO);
      redirectAttributes.addFlashAttribute("mensaje", "Solicitud creada exitosamente");
      redirectAttributes.addFlashAttribute("tipoMensaje", "success");
      return "redirect:/explorador/" + solicitudCreada;
    }
    catch (ValidationException e) {
      convertirValidationExceptionABindingResult(e, bindingResult);
      model.addAttribute("titulo", "Crear Nueva Solicitud");
      return "solicitudes/solicitudEliminacion";
    }
    catch (Exception e) {
      log.error("Error al crear solicitud", e);
      model.addAttribute("error", "Error al crear la solicitud: " + e.getMessage());
      model.addAttribute("titulo", "Crear Nueva Solicitud");
      return "solicitudes/solicitudEliminacion";
    }
  }

  //todo: abstraer
  private void convertirValidationExceptionABindingResult(ValidationException e, BindingResult bindingResult) {
    if(e.hasFieldErrors()) {
      e.getFieldErrors().forEach((field, error) -> bindingResult.rejectValue(field, "error." + field, error));
    }
  }


}

