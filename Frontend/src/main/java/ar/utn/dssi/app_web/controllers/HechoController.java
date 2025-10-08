package ar.utn.dssi.app_web.controllers;

import ar.utn.dssi.app_web.DTO.input.HechoInputDTO;
import ar.utn.dssi.app_web.DTO.output.HechoOutputDTO;
import ar.utn.dssi.app_web.services.HechoServices;
import lombok.RequiredArgsConstructor;
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

    private final HechoServices hechosService;

    @GetMapping("/nuevo")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("titulo", "Crear Nuevo Hecho");
        return "hechos/crearHecho";
    }

    @PostMapping("/crear")
    public String crearHecho(@ModelAttribute("hecho") HechoInputDTO hechoInputDTO,
                             BindingResult bindingResult,
                             Model model,
                             RedirectAttributes redirectAttributes) {

        try {
            HechoOutputDTO hechoCreado = hechosService.crearHecho(hechoInputDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "redirect:/";
    }

    @GetMapping("/mis_hechos")
    public String listarMisHechos(Model model) {
        //List<HechoDTO> hechos = hechosService.obtenerHechosDeUsuario(idUsuario).get();
        //model.addAttribute("hechos", hechos);
        model.addAttribute("titulo", "Mis Hechos");
        return "hechos/misHechos";
    }

    //TODO VER QUE ES ESTO
    @GetMapping("/detalle_hech_admin")
    public String detalleHechoAdmin() {
        return "hechos/detalleHechoAdmin";
    }

    @GetMapping("/gestion_hecho")
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
    public String hechosCoelccion(Model model) {
        model.addAttribute("titulo", "Hechos de Coleccion");
        return "hechos/listaHechosColeccion";
    }

    @GetMapping("/editar")
    public String mostrarFormularioEdicion(Model model) {
        model.addAttribute("titulo", "Editar Hecho");
        return "hechos/editarHecho";
    }
}
