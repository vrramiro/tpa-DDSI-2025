package ar.utn.dssi.app_web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/colecciones")
@RequiredArgsConstructor
public class ColeccionController {

    @GetMapping
    public String listarColecciones(Model model) {
        model.addAttribute("titulo", "Colecciones");
        return "colecciones/coleccionesSistema";
    }

    // Controllers para verificar funcionamiento de la vista (Borrar si no son necesarias)
    @GetMapping("/crearColeccion")
    public String detalleHechoAdmin() {
        return "colecciones/crearColecciones";
    }

    @GetMapping("/gestionColecciones")
    public String gestionHecho() {
        return "colecciones/gestionColeccionesAdmin";
    }

    @GetMapping("/modificar")
    public String hechoUnico() {
        return "colecciones/modificarColecciones";
    }
}
