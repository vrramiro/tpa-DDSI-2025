package ar.utn.dssi.app_web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/solicitudes")
@RequiredArgsConstructor
public class SolicitudController {

  @GetMapping("/gestion")
  public String gestionSolicitud(Model model) {
    model.addAttribute("titulo", "Gestion de Solicitud");
    return "solicitudes/gestionSolicitudesAdmin";
  }

  @GetMapping("/panel")
  public String panelSolicitud(Model model) {
    model.addAttribute("titulo", "Panel de Solicitudes");
    return "solicitudes/panelSolicitudAdmin";
  }

  @GetMapping("/nueva")
  public String nuevoSolicitud(Model model) {
    model.addAttribute("titulo", "Crear Nueva Solicitud");
    return "solicitudes/solicitudEliminacion";
  }
}
