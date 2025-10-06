package ar.utn.dssi.app_web.controllers;

import ar.utn.dssi.app_web.DTO.HechoDTO;
import ar.utn.dssi.app_web.services.HechoServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/hechos")
@RequiredArgsConstructor
public class HechoController {

    private final HechoServices hechosService;

    @GetMapping("/nuevo")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("titulo", "Crear Nuevo Hecho");
        return "hechos/crearHecho";
    }

    @GetMapping("/mis_hechos")
    public String listarMisHechos(Model model) {
        //List<HechoDTO> hechos = hechosService.obtenerHechosDeUsuario(idUsuario).get();
        //model.addAttribute("hechos", hechos);
        model.addAttribute("titulo", "Mis Hechos");
        return "hechos/misHechos";
    }

    // Controllers para verificar funcionamiento de la vista (Borrar si no son necesarias)
    @GetMapping("/detalleHechoAdmin")
    public String detalleHechoAdmin() {
        return "hechos/detalleHechoAdmin";
    }

    @GetMapping("/gestionHecho")
    public String gestionHecho() {
        return "hechos/gestionHechosAdmin";
    }

    @GetMapping("/hechoUnico")
    public String hechoUnico() {
        return "hechos/hecho";
    }

    @GetMapping("/listaHechosColeccion")
    public String hechosCoelccion() {
        return "hechos/listaHechosColeccion";
    }
}
