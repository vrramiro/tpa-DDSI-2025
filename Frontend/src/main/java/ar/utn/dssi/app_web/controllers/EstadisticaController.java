package ar.utn.dssi.app_web.controllers;

import ar.utn.dssi.app_web.dto.CategoriaDTO;
import ar.utn.dssi.app_web.dto.input.ColeccionResponseDTO;
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

import java.util.Comparator;
import java.util.List;

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
            List<ColeccionResponseDTO> colecciones = coleccionService.obtenerTodasLasColecciones();
            if (colecciones != null && !colecciones.isEmpty()) {
                colecciones.sort(Comparator.comparing(coleccion -> coleccion.getTitulo().toLowerCase()));
                model.addAttribute("colecciones", colecciones);
                String handle = colecciones.get(0).getHandle();
                model.addAttribute("statColDefault", estadisticasService.obtenerProvinciaPorColeccion(handle));
            }

            List<CategoriaDTO> categorias = categoriaService.obtenerCategorias();
            if (categorias != null && !categorias.isEmpty()) {
                categorias.sort(Comparator.comparing(c -> c.getCategoria().toLowerCase()));
                model.addAttribute("categorias", categorias);
                Long idPrimeraCat = categorias.get(0).getId();
                model.addAttribute("statCatProvDefault", estadisticasService.obtenerProvinciaPorCategoria(idPrimeraCat));
                model.addAttribute("statCatHoraDefault", estadisticasService.obtenerHorasPorCategoria(idPrimeraCat));
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error cargando listas.");
        }

        try {
            model.addAttribute("statSpam", estadisticasService.obtenerCantidadSpam());
            model.addAttribute("statCategoriaMax", estadisticasService.obtenerCategoriaMasHechos());
        } catch (Exception e) {
            log.error("No se pudieron cargar las cantidad de spam y/o las categorias con mas hechos. Causa: ", e);

            model.addAttribute("statSpam", 0);
            model.addAttribute("statCategoriaMax", "No disponible");
        }
        return "home/estadisticas";
    }

    @GetMapping("/estadisticas/api/coleccion/{handle}/provincia")
    @ResponseBody
    public ResponseEntity<EstadisticaInputDTO> provinciaPorColeccion(@PathVariable String handle) {
        return ResponseEntity.ok(estadisticasService.obtenerProvinciaPorColeccion(handle));
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
