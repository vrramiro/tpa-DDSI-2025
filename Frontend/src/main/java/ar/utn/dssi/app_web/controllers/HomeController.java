package ar.utn.dssi.app_web.controllers;

import ar.utn.dssi.app_web.dto.input.ColeccionResponseDTO;
import ar.utn.dssi.app_web.dto.output.HechoOutputDTO;
import ar.utn.dssi.app_web.services.Interfaces.IColeccionService;
import ar.utn.dssi.app_web.services.Interfaces.IHechoService;
import ar.utn.dssi.app_web.services.impl.HechoServices;
import org.springframework.beans.factory.annotation.Value;
import ar.utn.dssi.app_web.dto.Users.UserRequest;
import ar.utn.dssi.app_web.services.UsuariosApiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class HomeController {

  private final UsuariosApiService usuariosApiService;
  private final IHechoService hechosApiService;
  private final IColeccionService coleccionService;

  public HomeController(UsuariosApiService usuariosApiService, IHechoService hechosApiService, IColeccionService coleccionService) {
    this.usuariosApiService = usuariosApiService;
    this.hechosApiService = hechosApiService;
      this.coleccionService = coleccionService;
  }

  @Value("${agregador.service.url:http://localhost:8082}")
  private String agregadorUrl;
/*
  @GetMapping("/")
  public String landing(Model model) {
    model.addAttribute("agregadorUrl", agregadorUrl);
    return "home/landing";
  }
  */

  @GetMapping("/")
  public String landing(Model model) {
    final int CANTIDAD_DESTACADAS = 3;

    List<ColeccionResponseDTO> ultimasColecciones = coleccionService.obtenerUltimasColecciones(CANTIDAD_DESTACADAS);
    model.addAttribute("coleccionesDestacadas", ultimasColecciones);

    List<HechoOutputDTO> hechosRecientes =
            hechosApiService.obtenerHechosRecientes(3);

    model.addAttribute("hechosRecientes", hechosRecientes);

    return "home/landing";
  }


  @GetMapping("/login")
  public String login(Model model) {
    model.addAttribute("titulo", "Inicio Sesión");
    if (model.containsAttribute("mensaje")) {}
    return "home/inicioSesion";
  }


  @GetMapping("/registro")
  public String mostrarFormularioRegistro(Model model) {
    model.addAttribute("titulo", "Registro");
    model.addAttribute("userRequest", new UserRequest());
    return "home/registro";
  }

  @PostMapping("/crear_cuenta")
  public String registro(@ModelAttribute("userRequest") UserRequest request,
                         RedirectAttributes redirectAttributes) {

    try {
      boolean exitoso = usuariosApiService.registroUsuario(request);

      if (exitoso) {
        redirectAttributes.addFlashAttribute("mensaje", "Cuenta creada correctamente. ¡Ya puedes iniciar sesión!");
        redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        return "redirect:/login";
      } else {
        redirectAttributes.addFlashAttribute("mensaje", "No se pudo crear la cuenta. El usuario ya existe o hubo un error.");
        redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        return "redirect:/login";
      }
    } catch (Exception e) {
      System.err.println("Error en registro: " + e.getMessage());
      redirectAttributes.addFlashAttribute("mensaje", "Ocurrió un error inesperado: " + e.getMessage());
      redirectAttributes.addFlashAttribute("tipoMensaje", "error");
      return "redirect:/login";
    }
  }

  @GetMapping("/panel_gestion")
  public String gestion(Model model) {
    model.addAttribute("titulo", "Panel de Gestion");
    return "home/panelGestion";
  }

  @GetMapping("/404")
  public String error404(Model model) {
    model.addAttribute("titulo", "Error 404");
    return "errores/404";
  }

  @GetMapping("/403")
  public String error403(Model model) {
    model.addAttribute("titulo", "Error 403");
    return "errores/403";
  }

  @GetMapping("/privacidad")
  public String privacidad(Model model) {
    model.addAttribute("titulo", "Política de Privacidad");
    return "home/politicasPrivacidad";
  }
}

