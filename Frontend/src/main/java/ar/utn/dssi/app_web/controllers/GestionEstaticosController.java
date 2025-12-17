package ar.utn.dssi.app_web.controllers;

import ar.utn.dssi.app_web.services.Interfaces.IHechoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/panelGestion/estatica")
@RequiredArgsConstructor
public class GestionEstaticosController {
    private final IHechoService hechoServices;

    @PostMapping("/importar")
    public String importarHechos(@RequestParam("archivo") MultipartFile file, RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("mensaje", "Por favor, seleccione un archivo para importar.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
            return "redirect:/panel_gestion";
        }

        try {
            hechoServices.crearHechoEstatico(file);
            redirectAttributes.addFlashAttribute("mensaje", "Archivo procesado exitosamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
            return "redirect:/panel_gestion";

        } catch (RuntimeException e) {
            String errorMessage = "Ocurrió un error inesperado al importar.";

            if (e.getCause() instanceof WebClientResponseException) {
                WebClientResponseException webEx = (WebClientResponseException) e.getCause();
                errorMessage = "Error de la API (" + webEx.getStatusCode() + "): " + webEx.getResponseBodyAsString();
            } else if (e.getMessage() != null) {
                errorMessage = e.getMessage();
            }

            redirectAttributes.addFlashAttribute("mensaje", errorMessage);
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
            return "redirect:/panel_gestion";
        }
    }
}