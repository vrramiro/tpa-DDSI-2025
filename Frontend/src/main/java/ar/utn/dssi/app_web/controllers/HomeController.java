package ar.utn.dssi.app_web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("titulo", "Home");
        return "home/landing";
    }

    @GetMapping("/explorador")
    public String mapa(Model model) {
        model.addAttribute("titulo", "Explorador");
        return "home/explorador";
    }

    @GetMapping("/panel_gestion")
    public String gestion(Model model) {
        model.addAttribute("titulo", "Panel de Gestion");
        return "home/panelGestion";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("titulo", "Inicio Sesion");
        return "home/inicioSesion";
    }

    @GetMapping("/registro")
    public String registro(Model model) {
        model.addAttribute("titulo", "Registro");
        return "home/registro";
    }

    @GetMapping("/estadisticas")
    public String estadisticas(Model model) {
        model.addAttribute("titulo", "Estadisticas");
        return "home/estadisticas";
    }
}
