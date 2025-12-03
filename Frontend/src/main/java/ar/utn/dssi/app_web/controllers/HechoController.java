package ar.utn.dssi.app_web.controllers;

import ar.utn.dssi.app_web.dto.CategoriaDTO;
import ar.utn.dssi.app_web.dto.EstadoHecho;
import ar.utn.dssi.app_web.dto.input.HechoRequest;
import ar.utn.dssi.app_web.dto.input.PageResponseDTO;
import ar.utn.dssi.app_web.dto.output.HechoOutputDTO;
import ar.utn.dssi.app_web.error.UbicacionInvalida;
import ar.utn.dssi.app_web.error.ValidationException;
import ar.utn.dssi.app_web.mappers.HechoMapper;
import ar.utn.dssi.app_web.services.Interfaces.ICategoriaService;
import ar.utn.dssi.app_web.services.Interfaces.IHechoService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/hechos")
@RequiredArgsConstructor
public class HechoController {

  private static final Logger log = LoggerFactory.getLogger(HechoController.class);
  private final IHechoService hechosService;
  private final ICategoriaService categoriaService;

  @GetMapping("/nuevo")
  @PreAuthorize("hasAnyRole('CONTRIBUYENTE', 'ADMINISTRADOR')")
  public String mostrarFormularioCrear(Model model) {
    model.addAttribute("titulo", "Crear Nuevo Hecho");
    model.addAttribute("hecho", new HechoRequest());
    model.addAttribute("categorias", categoriaService.obtenerCategorias());
    return "hechos/crearHecho";
  }

  @PostMapping("/crear")
  @PreAuthorize("hasAnyRole('CONTRIBUYENTE', 'ADMINISTRADOR')")
  public String crearHecho(@ModelAttribute("hecho") HechoRequest hechoRequest,
                           BindingResult bindingResult,
                           Model model) {

    try {
      boolean exitoso = hechosService.crearHecho(hechoRequest);
      if (exitoso) {
        model.addAttribute("mensaje", "El hecho fue subido correctamente, está siendo procesado.");
        model.addAttribute("tipoMensaje", "success");
        model.addAttribute("urlRedireccion", "/explorador");
      } else {
        model.addAttribute("mensaje", "Error al cargar el hecho, inténtelo nuevamente en un rato.");
        model.addAttribute("tipoMensaje", "error");
      }
      model.addAttribute("titulo", "Crear Nuevo Hecho");
      model.addAttribute("categorias", categoriaService.obtenerCategorias());
      return "hechos/crearHecho";
    } catch (UbicacionInvalida e) {
      bindingResult.rejectValue("latitud", "ubicacionInvalida", "La ubicación debe estar dentro de Argentina");
      bindingResult.rejectValue("longitud", "ubicacionInvalida", "La ubicación debe estar dentro de Argentina");
      model.addAttribute("titulo", "Crear Nuevo Hecho");
      model.addAttribute("categorias", categoriaService.obtenerCategorias());
      return "hechos/crearHecho";
    } catch (ValidationException e) {
      convertirValidationExceptionABindingResult(e, bindingResult);
      model.addAttribute("titulo", "Crear Nuevo Hecho");
      model.addAttribute("categorias", categoriaService.obtenerCategorias());
      return "hechos/crearHecho";
    } catch (Exception e) {
      log.error("Error al crear hecho", e);
      model.addAttribute("mensaje", "Error inesperado al crear el hecho: " + e.getMessage());
      model.addAttribute("tipoMensaje", "error");
      model.addAttribute("titulo", "Crear Nuevo Hecho");
      model.addAttribute("categorias", categoriaService.obtenerCategorias());
      return "hechos/crearHecho";
    }
  }

  @GetMapping("/{id}")
  public String hechoUnico(@PathVariable Long id, Model model) {
    Optional<HechoOutputDTO> hechoOutputDTO = hechosService.obtenerHechoPorId(id);
    if (hechoOutputDTO.isEmpty()) {
      return "redirect:/404";
    }

    model.addAttribute("titulo", hechoOutputDTO.get().getTitulo());
    model.addAttribute("hecho", hechoOutputDTO.get());
    return "hechos/hecho";
  }

  @PostMapping("/{id}/aceptar")
  public String aceptarHecho(@PathVariable Long id, RedirectAttributes redirectAttributes) {
    try {
      hechosService.cambiarEstadoHecho(id, EstadoHecho.ACEPTADO);
      redirectAttributes.addFlashAttribute("mensaje", "Hecho aceptado correctamente");
      redirectAttributes.addFlashAttribute("tipo", "success");
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("mensaje", "Error al aceptar el hecho");
      redirectAttributes.addFlashAttribute("tipo", "error");
    }
    return "redirect:/hechos/" + id;
  }
/* TODO
  @PreAuthorize("hasAuthority('ADMINISTRADOR')")
  @GetMapping("/{id}/sugerir")
  public String mostrarFormularioSugerencia(@PathVariable Long id, Model model) {
    Optional<HechoOutputDTO> hecho = hechosService.obtenerHechoPorId(id);
    model.addAttribute("hecho", hecho);
    return "hechos/sugerirHecho";
  }

  @PreAuthorize("hasAuthority('ADMINISTRADOR')")
  @PostMapping("/{id}/sugerir")
  public String enviarSugerencia(@PathVariable Long id,
                                 @RequestParam(required = false) String sugerencia,
                                 RedirectAttributes redirectAttributes) {
    try {
      if (sugerencia != null && !sugerencia.isBlank()) {
        hechosService.registrarSugerencia(id, sugerencia);
        redirectAttributes.addFlashAttribute("mensaje", "Sugerencia enviada correctamente");
      } else {
        hechosService.cambiarEstadoHecho(id, EstadoHecho.ACEPTADO);
        redirectAttributes.addFlashAttribute("mensaje", "Hecho aceptado sin sugerencia");
      }
      redirectAttributes.addFlashAttribute("tipo", "success");
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("mensaje", "Error al procesar la acción");
      redirectAttributes.addFlashAttribute("tipo", "error");
    }
    return "redirect:/hechos/" + id;
  }
*/

  @PostMapping("/{id}/rechazar")
  public String rechazarHecho(@PathVariable Long id, RedirectAttributes redirectAttributes) {
    try {
      hechosService.cambiarEstadoHecho(id, EstadoHecho.RECHAZADO);
      redirectAttributes.addFlashAttribute("mensaje", "Hecho rechazado correctamente");
      redirectAttributes.addFlashAttribute("tipo", "success");
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("mensaje", "Error al rechazar el hecho");
      redirectAttributes.addFlashAttribute("tipo", "error");
    }
    return "redirect:/hechos/" + id;
  }

  @GetMapping("/{id}/editar")
  public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
    Optional<HechoOutputDTO> hechoOutputOpt = hechosService.obtenerHechoPorId(id);

    if (hechoOutputOpt.isEmpty()) {
      return "redirect:/404";
    }

    HechoOutputDTO hechoOutput = hechoOutputOpt.get();

    HechoRequest hechoInput = new HechoRequest();
    hechoInput.setTitulo(hechoOutput.getTitulo());
    hechoInput.setDescripcion(hechoOutput.getDescripcion());
    hechoInput.setFechaAcontecimiento(hechoOutput.getFechaAcontecimiento());

    List<CategoriaDTO> categorias = categoriaService.obtenerCategorias();
    categorias.stream()
            .filter(c -> c.getCategoria().equals(hechoOutput.getCategoria()))
            .findFirst()
            .ifPresent(cat -> hechoInput.setIdCategoria(cat.getId()));

    if (hechoOutput.getUbicacion() != null) {
      hechoInput.setLatitud(hechoOutput.getUbicacion().getLatitud());
      hechoInput.setLongitud(hechoOutput.getUbicacion().getLongitud());
    }

    model.addAttribute("hecho", hechoInput);
    model.addAttribute("hechoOutput", hechoOutput);
    model.addAttribute("categorias", categorias);
    model.addAttribute("titulo", "Editar Hecho");

    return "hechos/editarHecho";
  }

  @PostMapping("/{id}/editar")
  public String editarHecho(@PathVariable Long id,
                            @ModelAttribute("hecho") HechoRequest hechoRequest,
                            BindingResult bindingResult,
                            Model model) {
    try {
      if (hechoRequest.getTitulo().isBlank())
        bindingResult.rejectValue("titulo", "error", "El título es obligatorio");

      if (bindingResult.hasErrors()) {
        return recargarFormularioError(id, hechoRequest, model);
      }

      boolean exitoso = hechosService.crearSolicitudEdicion(id, hechoRequest);

      if (exitoso) {
        model.addAttribute("mensaje", "Tu solicitud de edición ha sido enviada correctamente. Un administrador la revisará.");
        model.addAttribute("tipoMensaje", "success");
      } else {
        model.addAttribute("mensaje", "Error al enviar la solicitud. Intenta nuevamente.");
        model.addAttribute("tipoMensaje", "error");
      }

      return recargarFormularioError(id, hechoRequest, model);

    } catch (Exception e) {
      model.addAttribute("mensaje", "Error inesperado: " + e.getMessage());
      model.addAttribute("tipoMensaje", "error");
      return recargarFormularioError(id, hechoRequest, model);
    }
  }

  private String recargarFormularioError(Long id, HechoRequest request, Model model) {
    model.addAttribute("hechoOutput", hechosService.obtenerHechoPorId(id).orElse(null));
    model.addAttribute("categorias", categoriaService.obtenerCategorias());
    model.addAttribute("titulo", "Editar Hecho");
    return "hechos/editarHecho";
  }

  @GetMapping("/misHechos")
  public String listarMisHechos(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "9") int size, // Volvemos a un número razonable
                                @RequestParam(required = false) String filtro,
                                @RequestParam(required = false, defaultValue = "titulo,asc") String sort,
                                Model model) {

    List<HechoOutputDTO> todosMisHechos = hechosService.obtenerMisHechos();

    if (filtro != null && !filtro.isEmpty()) {
      todosMisHechos = todosMisHechos.stream()
              .filter(h -> h.getTitulo().toLowerCase().contains(filtro.toLowerCase()))
              .toList();
    }

    int totalElements = todosMisHechos.size();
    int totalPages = (int) Math.ceil((double) totalElements / size);
    if (totalPages == 0) totalPages = 1;

    if (page < 0) page = 0;
    if (page >= totalPages) page = totalPages - 1;

    int fromIndex = page * size;
    int toIndex = Math.min(fromIndex + size, totalElements);

    List<HechoOutputDTO> hechosPaginados;
    if (totalElements == 0) {
      hechosPaginados = List.of();
    } else {
      hechosPaginados = todosMisHechos.subList(fromIndex, toIndex);
    }

    model.addAttribute("hechos", hechosPaginados);
    model.addAttribute("page", page);             // Variable 'current' en el fragmento
    model.addAttribute("size", size);
    model.addAttribute("sort", sort);
    model.addAttribute("filtro", filtro == null ? "" : filtro);
    model.addAttribute("totalPages", totalPages);
    model.addAttribute("totalElements", totalElements);
    model.addAttribute("baseUrl", "/hechos/misHechos");

    model.addAttribute("isFirst", page == 0);
    model.addAttribute("isLast", page == totalPages - 1);

    model.addAttribute("titulo", "Mis Hechos");

    return "hechos/misHechos";
  }

  @GetMapping("/gestion_hecho")
  public String gestionHecho(@RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "10") int size,
                             @RequestParam(required = false) String estado,
                             @RequestParam(required = false, defaultValue = "fechaCarga,desc") String sort,
                             Model model) {

    EstadoHecho estadoEnum = null;
    if (estado != null && !estado.trim().isEmpty()) {
      try {
        estadoEnum = EstadoHecho.valueOf(estado.toUpperCase());
      } catch (IllegalArgumentException e) {
        estadoEnum = null;
      }
    }

    String filtroEstado = Optional.ofNullable(estadoEnum)
            .map(Enum::name)
            .orElse(null);

    PageResponseDTO<HechoOutputDTO> pageResponseDTO = hechosService.listarHechos(page);

    model.addAttribute("hechos", pageResponseDTO.getContent());
    model.addAttribute("page", page);
    model.addAttribute("size", size);
    model.addAttribute("sort", sort);
    model.addAttribute("estado", estado == null ? "" : estado);
    model.addAttribute("totalPages", pageResponseDTO.getTotalPages());
    model.addAttribute("totalElements", pageResponseDTO.getTotalElements());
    model.addAttribute("titulo", "Gestión de Hechos");
    model.addAttribute("baseUrl", "/hechos/gestion_hecho");

    return "hechos/gestionHechosAdmin";
  }

  private void convertirValidationExceptionABindingResult(ValidationException e, BindingResult bindingResult) {
    if (e.hasFieldErrors()) {
      e.getFieldErrors().forEach((field, error) -> bindingResult.rejectValue(field, "error." + field, error));
    }
  }

  @GetMapping("/explorador")
  public String exploradorConFiltros(Model model,
                                     @RequestParam(required = false) Long idHecho,
                                     @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaAcontecimientoDesde,
                                     @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaAcontecimientoHasta,
                                     @RequestParam(required = false) Long idCategoria,
                                     @RequestParam(required = false) String provincia,
                                     @RequestParam(required = false) Long idColeccion
  ) {

    log.info("--- Filtros recibidos en /explorador ---");
    log.info("idHecho: {}", idHecho);
    log.info("idCategoria: {}", idCategoria);
    log.info("provincia: {}", provincia);
    log.info("fechaDesde: {}", fechaAcontecimientoDesde);
    log.info("fechaHasta: {}", fechaAcontecimientoHasta);
    log.info("idColeccion: {}", idColeccion);
    // log.info("modoCurado: {}", modoCurado);
    log.info("-----------------------------------------");

      model.addAttribute("titulo", "Explorador");

      List<CategoriaDTO> categorias = categoriaService.obtenerCategorias();
      categorias.sort(Comparator.comparing(CategoriaDTO::getCategoria));
      model.addAttribute("categorias", categorias);

      model.addAttribute("provincias", hechosService.obtenerProvincias());

      if(idHecho != null) {

        Optional<HechoOutputDTO> hechoOptional = hechosService.obtenerHechoPorId(idHecho);
        if (hechoOptional.isPresent()) {
          HechoOutputDTO hechoDTO = hechoOptional.get();
          model.addAttribute("hechos", List.of(hechoDTO));
          model.addAttribute("centroMapaLat", hechoDTO.getUbicacion().getLatitud());
          model.addAttribute("centroMapaLng", hechoDTO.getUbicacion().getLongitud());
        }

      } else if (idColeccion != null) {

        // Lógica para colecciones (si es diferente)
        // model.addAttribute("hechos", gestionHechosApiService.obtenerHechosDeColeccion(idColeccion, ...));

      } else {

        List<HechoOutputDTO> hechosFiltrados = hechosService.obtenerHechos(
                fechaAcontecimientoDesde,
                fechaAcontecimientoHasta,
                idCategoria,
                provincia
                //TODO modoCurado??
        );
        model.addAttribute("hechos", hechosFiltrados);
      }

      model.addAttribute("filtroProvincia", provincia);
      model.addAttribute("filtroIdCategoria", idCategoria);
      model.addAttribute("filtroFechaDesde", fechaAcontecimientoDesde);
      model.addAttribute("filtroFechaHasta", fechaAcontecimientoHasta);
      //TODO model.addAttribute("filtroModoCurado", modoCurado);???

      return "home/explorador";
    }


/***********************************************************************************************************************/
/***************************************************LO DE ABAJO FALTA***************************************************/
/***********************************************************************************************************************/


  //TODO VERLO CON SANTI
  /*@GetMapping("/lista_hechos_coleccion")
  public String hechosCoelccion(Model model) {
    model.addAttribute("titulo", "Hechos de Coleccion");
    return "hechos/listaHechosColeccion";
  }*/
}

