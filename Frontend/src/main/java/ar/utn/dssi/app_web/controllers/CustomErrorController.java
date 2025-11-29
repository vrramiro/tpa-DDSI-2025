package ar.utn.dssi.app_web.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String vistaError = "errores/500"; // Vista por defecto para errores genéricos
        String titulo = "Error";

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                // Error 404
                vistaError = "errores/404";
                titulo = "Página no encontrada";
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                // Error 403
                vistaError = "errores/403";
                titulo = "Acceso Denegado";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                // Error 500 (como el de tus capturas)
                vistaError = "errores/500";
                titulo = "Error Interno";
            }

            model.addAttribute("statusCode", statusCode);
        } else {
            model.addAttribute("statusCode", "Error");
        }

        // Agregamos el título que espera tu plantilla base.html
        model.addAttribute("titulo", titulo);

        return vistaError; // Retorna la plantilla de Thymeleaf correspondiente
    }
}