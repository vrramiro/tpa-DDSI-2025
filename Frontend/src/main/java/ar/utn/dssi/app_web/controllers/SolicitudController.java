package ar.utn.dssi.app_web.controllers;

import ar.utn.dssi.app_web.dto.output.HechoOutputDTO;
import ar.utn.dssi.app_web.dto.SolicitudDTO;
import ar.utn.dssi.app_web.error.ValidationException;
import ar.utn.dssi.app_web.services.GestionHechosApiService;
import ar.utn.dssi.app_web.services.impl.GestionSolicitudesApiService;
import ar.utn.dssi.app_web.services.impl.SolicitudService;
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

  private static final Logger log = LoggerFactory.getLogger(SolicitudController.class);
  private final GestionSolicitudesApiService solicitudService;
  private final GestionHechosApiService hechosApiService;

  @PreAuthorize("hasAnyRole('ADMINISTRADOR')")
  @GetMapping("/gestion")
  public String gestionSolicitudes(
          @RequestParam(name = "estado", required = false, defaultValue = "Todos") String estado,
          Model model) {

    List<SolicitudDTO> solicitudes;
    if (estado.equalsIgnoreCase("Todos")) {
      solicitudes = solicitudService.obtenerSolicitudesEliminacion();
    } else {
      solicitudes = solicitudService.obtenerSolicitudesPorEstado(estado);
    }

    model.addAttribute("titulo", "Gestión de Solicitudes");
    model.addAttribute("solicitudes", solicitudes);
    model.addAttribute("estadoSeleccionado", estado);
    return "solicitudes/gestionSolicitudesAdmin";
  }

  @PreAuthorize("hasAnyRole('ADMINISTRADOR')")
  @GetMapping("/panel/solicitudId")
  public String panelSolicitudes(@RequestParam(name = "id") Long solicitudId,
                                 Model model) {
    SolicitudDTO solicitud = solicitudService.obtenerSolicitudPorId(solicitudId);

    HechoOutputDTO hecho = (solicitud.getHecho() != null) ? solicitud.getHecho() : new HechoOutputDTO();

    model.addAttribute("solicitud", solicitud);
    model.addAttribute("hecho", hecho);
    model.addAttribute("titulo", "Panel de Solicitudes");
    return "solicitudes/panelSolicitudAdmin";
  }


  // CREACION DE SOLICITUDES
  @GetMapping("/crearSolicitud")
  public String mostrarFormularioCrear(@RequestParam(name = "hechoId", required = false) Long hechoId, Model model) {
    model.addAttribute("titulo", "Crear Nueva Solicitud");

    SolicitudDTO solicitud = new SolicitudDTO();
    HechoOutputDTO hechoDto = new HechoOutputDTO();

    if (hechoId != null) {
      hechoDto = hechosApiService.obtenerHechoPorId(hechoId);
    }

    solicitud.setHecho(hechoDto);
    model.addAttribute("solicitud", solicitud);
    return "solicitudes/solicitudEliminacion";
  }

  @PostMapping("/crear")
  public String nuevaSolicitud(@ModelAttribute("solicitud") SolicitudDTO solicitudOutputDTO,
                               BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
    try {
      SolicitudDTO solicitudCreada = solicitudService.crearSolicitud(solicitudOutputDTO);
      redirectAttributes.addFlashAttribute("mensaje", "Solicitud creada exitosamente");
      redirectAttributes.addFlashAttribute("tipoMensaje", "success");
      return "redirect:/hechos/misHechos"; //TODO: AGREGAR VISTA DE CREADO EXITOSO
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

  @PostMapping("/procesar")
  public String procesarSolicitud(@RequestParam Long solicitudId,
                                  @RequestParam String estado,
                                  RedirectAttributes redirectAttributes) {
    try {
      if (!estado.equals("ACEPTADA") && !estado.equals("RECHAZADA")) {
        throw new IllegalArgumentException("Estado inválido");
      }
      solicitudService.procesarSolicitud(solicitudId, estado);
      String mensaje = estado.equals("ACEPTADA") ? "Solicitud aceptada y hecho eliminado." : "Solicitud rechazada.";
      redirectAttributes.addFlashAttribute("mensaje", mensaje);
      redirectAttributes.addFlashAttribute("tipoMensaje", "success");

    } catch (Exception e) {
      log.error("Error procesando solicitud", e);
      redirectAttributes.addFlashAttribute("error", "Error al procesar la solicitud: " + e.getMessage());
    }
    return "redirect:/solicitudes/gestion";
  }

  private void convertirValidationExceptionABindingResult(ValidationException e, BindingResult bindingResult) {
    if(e.hasFieldErrors()) {
      e.getFieldErrors().forEach((field, error) -> bindingResult.rejectValue(field, "error." + field, error));
    }
  }


}

