package ar.utn.dssi.app_web.controllers;

import ar.utn.dssi.app_web.dto.input.EstadisticaInputDTO;
import ar.utn.dssi.app_web.services.Interfaces.ICategoriaService;
import ar.utn.dssi.app_web.services.Interfaces.IColeccionService;
import ar.utn.dssi.app_web.services.Interfaces.IEstadisticaService;
import io.github.classgraph.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequiredArgsConstructor
public class EstadisticaController {

    private final IColeccionService coleccionService;
    private final ICategoriaService categoriaService;
    private final IEstadisticaService estadisticasService;

    @GetMapping("/estadisticas")
    public String estadisticas(Model model) {
        model.addAttribute("titulo", "Estadisticas");

        try {
            //model.addAttribute("colecciones", coleccionService.listarColecciones());
            model.addAttribute("categorias", categoriaService.obtenerCategorias());
        } catch (Exception e) {
            model.addAttribute("error", "Error cargando listas.");
        }

        try {
            model.addAttribute("statSpam", estadisticasService.obtenerCantidadSpam());
            model.addAttribute("statCategoriaMax", estadisticasService.obtenerCategoriaMasHechos());
        } catch (Exception e) {
            log.error("No se pudieron cargar las cantidad de spam y/o las categorias con mas hechos");
        }
        return "home/estadisticas";
    }

    @GetMapping("/estadisticas/api/coleccion/{id}/provincia")
    @ResponseBody
    public ResponseEntity<EstadisticaInputDTO> provinciaPorColeccion(@PathVariable Long id) {
        return ResponseEntity.ok(estadisticasService.obtenerProvinciaPorColeccion(id));
    }

    @GetMapping("/estadisticas/api/categoria/{id}/provincia")
    @ResponseBody
    public ResponseEntity<EstadisticaInputDTO> provinciaPorCategoria(@PathVariable Long id) {
        return ResponseEntity.ok(estadisticasService.obtenerProvinciaPorCategoria(id));
    }

    @GetMapping("/estadisticas/api/categoria/{id}/horas")
    @ResponseBody
    public ResponseEntity<EstadisticaInputDTO> horasPorCategoria(@PathVariable Long id) {
        return ResponseEntity.ok(estadisticasService.obtenerHorasPorCategoria(id));
    }

    @GetMapping("/estadisticas/exportar")
    public ResponseEntity<Resource> exportar(@RequestParam(defaultValue = "CSV") String tipo) {
        try {
            byte[] archivo = estadisticasService.exportarEstadisticas(tipo);
            ByteArrayResource resource = new ByteArrayResource(archivo);

            /*return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=estadisticas.csv")
                    .contentLength(archivo.length)
                    .contentType(MediaType.parseMediaType("text/csv"))
                    .body(resource);*/
            return null;

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
