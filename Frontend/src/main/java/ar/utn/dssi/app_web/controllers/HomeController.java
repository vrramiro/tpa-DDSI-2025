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
}
