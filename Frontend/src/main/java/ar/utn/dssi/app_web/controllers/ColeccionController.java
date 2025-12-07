package ar.utn.dssi.app_web.controllers;

import ar.utn.dssi.app_web.dto.CategoriaDTO;
import ar.utn.dssi.app_web.dto.Consenso.ConsensoDTO;
import ar.utn.dssi.app_web.dto.Consenso.TipoConsenso;
import ar.utn.dssi.app_web.dto.Criterio.CriterioDTO;
import ar.utn.dssi.app_web.dto.Criterio.TipoCriterio;
import ar.utn.dssi.app_web.dto.Fuente.TipoFuente;
import ar.utn.dssi.app_web.dto.input.ColeccionResponseDTO;
import ar.utn.dssi.app_web.dto.input.PageResponseDTO;
import ar.utn.dssi.app_web.dto.output.ColeccionRequestDTO;
import ar.utn.dssi.app_web.dto.output.HechoOutputDTO;

import ar.utn.dssi.app_web.services.Interfaces.ICategoriaService;
import ar.utn.dssi.app_web.services.Interfaces.IColeccionService;
import ar.utn.dssi.app_web.services.Interfaces.IHechoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/colecciones")
public class ColeccionController {
  private static final Logger log = LoggerFactory.getLogger(ColeccionController.class);
  private final IColeccionService coleccionService;
  private final IHechoService hechosService;
  private final ICategoriaService categoriaService;

  public ColeccionController(IColeccionService coleccionService, IHechoService hechosService, ICategoriaService categoriaService) {
    this.coleccionService = coleccionService;
    this.hechosService = hechosService;
    this.categoriaService = categoriaService;
  }

  @GetMapping
  public String listarColecciones(@RequestParam(defaultValue = "0") Integer page,
                                  @RequestParam(defaultValue = "12") Integer size, // Recibimos size
                                  Model model) {
    PageResponseDTO<ColeccionResponseDTO> pageColeccion;

    try {
      // Llamamos al servicio con page y size
      pageColeccion = coleccionService.listarColecciones(page, size);

      if(pageColeccion == null) {
        pageColeccion = new PageResponseDTO<>();
      }

      model.addAttribute("colecciones", pageColeccion.getContent());

      // Pasamos los datos de paginación a la vista
      model.addAttribute("page", pageColeccion.getNumber());
      model.addAttribute("size", pageColeccion.getSize()); // Asegurate de que el DTO tenga este valor correcto
      model.addAttribute("totalPages", pageColeccion.getTotalPages());
      model.addAttribute("totalElements", pageColeccion.getTotalElements());
      model.addAttribute("isFirst", pageColeccion.isFirst());
      model.addAttribute("isLast", pageColeccion.isLast());

      model.addAttribute("titulo", "Colecciones");
      model.addAttribute("baseUrl", "/colecciones"); // Url base para los botones del paginador

      return "colecciones/lista_colecciones";

    } catch (Exception ex) {
      log.error("Error al listar colecciones", ex);
      return "redirect:/404";
    }
  }

  @GetMapping("/{handle}/hechos")
  public String listarHechosDeColeccion(@PathVariable("handle") String handle,
                                        @RequestParam(defaultValue = "0") Integer page, // Recibir página
                                        Model model) {

    Optional<ColeccionResponseDTO> coleccionOpt = coleccionService.obtenerColeccion(handle);
    if (coleccionOpt.isEmpty()) {
      return "redirect:/404";
    }
    PageResponseDTO<HechoOutputDTO> pageHechos = hechosService.listarHechosDeColeccion(handle, page);

    model.addAttribute("coleccion", coleccionOpt.get());
    model.addAttribute("hechos", pageHechos.getContent());
    model.addAttribute("titulo", "Colección: " + coleccionOpt.get().getTitulo());

    model.addAttribute("page", pageHechos.getNumber());
    model.addAttribute("size", pageHechos.getSize());
    model.addAttribute("totalPages", pageHechos.getTotalPages());
    model.addAttribute("totalElements", pageHechos.getTotalElements());
    model.addAttribute("isFirst", pageHechos.isFirst());
    model.addAttribute("isLast", pageHechos.isLast());

    model.addAttribute("baseUrl", "/colecciones/" + handle + "/hechos");

    return "hechos/listaHechosColeccion";
  }

  @GetMapping("/nuevo")
  public String mostrarFormularioCrear(Model model) {
    ColeccionRequestDTO coleccion = new ColeccionRequestDTO();
    coleccion.setConsenso(new ConsensoDTO());
    coleccion.setCategoria(new CategoriaDTO());
    coleccion.setFuentes(new ArrayList<>());

    List<CriterioDTO> listaCriterios = inicializarCriterios(coleccion);
    coleccion.setCriterios(listaCriterios);

    model.addAttribute("categoriasDisponibles", categoriaService.obtenerCategorias());
    model.addAttribute("coleccion", coleccion);
    model.addAttribute("tiposDeConsenso", TipoConsenso.values());
    model.addAttribute("tiposDeFuentes", TipoFuente.values());
    model.addAttribute("titulo", "Crear Colección");

    return "colecciones/crearColecciones";
  }

  @PostMapping("/crear")
  public String crearColeccion(@ModelAttribute("coleccion") ColeccionRequestDTO coleccionCreada,
                               BindingResult binding,
                               Model model,
                               RedirectAttributes redirectAttributes) {

    if (binding.hasErrors()) {
      cargarDatosFormulario(model); // Método helper para no repetir código
      model.addAttribute("mensaje", "Verifique los datos ingresados.");
      model.addAttribute("tipoMensaje", "error");
      return "colecciones/crearColecciones";
    }

    try {
      ColeccionResponseDTO respuesta = coleccionService.crearColeccion(coleccionCreada);

      redirectAttributes.addFlashAttribute("mensaje", "Colección creada exitosamente.");
      redirectAttributes.addFlashAttribute("tipoMensaje", "success");
      redirectAttributes.addFlashAttribute("coleccionCreada", respuesta);
      return "redirect:/colecciones";

    } catch (Exception e) {

      String mensajeLimpio = e.getMessage().replace("Error al crear: ", "");

      model.addAttribute("mensaje", mensajeLimpio);
      model.addAttribute("tipoMensaje", "error");

      cargarDatosFormulario(model);

      model.addAttribute("coleccion", coleccionCreada);

      return "colecciones/crearColecciones"; // Volvemos a la vista, NO redirect
    }
  }

  @GetMapping("/gestion_colecciones")
  public String gestionColeccion(@RequestParam(defaultValue = "0") Integer page,
                                 @RequestParam(defaultValue = "12") Integer size,
                                 @RequestParam(required = false) String filtro,
                                 @RequestParam(required = false, defaultValue = "titulo,asc") String sort,
                                 Model model) {

    model.addAttribute("titulo", "Gestión de Colecciones");

    PageResponseDTO<ColeccionResponseDTO> pageColeccionResponseDTO = coleccionService.listarColecciones(page, size);

    model.addAttribute("colecciones", pageColeccionResponseDTO.getContent());

    model.addAttribute("page", pageColeccionResponseDTO.getNumber()); // Usar el número real que devolvió el backend
    model.addAttribute("size", pageColeccionResponseDTO.getSize());   // Usar el size real
    model.addAttribute("totalPages", pageColeccionResponseDTO.getTotalPages());
    model.addAttribute("totalElements", pageColeccionResponseDTO.getTotalElements());

    model.addAttribute("sort", sort);
    model.addAttribute("filtro", filtro == null ? "" : filtro);

    model.addAttribute("isFirst", pageColeccionResponseDTO.isFirst());
    model.addAttribute("isLast", pageColeccionResponseDTO.isLast());

    model.addAttribute("baseUrl", "/colecciones/gestion_colecciones");

    return "colecciones/gestionColeccionesAdmin";
  }

  @PostMapping("{id}/eliminar")
  public String eliminarColeccion(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
    coleccionService.eliminarColeccion(id);
    redirectAttributes.addFlashAttribute("mensaje", "Colección eliminada");
    redirectAttributes.addFlashAttribute("tipoMensaje", "success");
    return "redirect:/colecciones/gestion_colecciones";
  }

  @GetMapping("{handle}/modificar")
  public String mostrarFormularioModificar(@PathVariable String handle, Model model) {
    Optional<ColeccionResponseDTO> coleccion = coleccionService.obtenerColeccion(handle);

    model.addAttribute("tiposDeConsenso", TipoConsenso.values());
    model.addAttribute("tiposDeFuentes", TipoFuente.values());
    model.addAttribute("categorias", categoriaService.obtenerCategorias());
    model.addAttribute("coleccion", coleccion);

    model.addAttribute("titulo", "Editar Colección");
    return "colecciones/modificarColecciones";
  }

  @PostMapping("{id}/actualizar")
  public String actualizarColeccion(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
    coleccionService.actualizarColeccion(id);
    redirectAttributes.addFlashAttribute("mensaje", "Colección Actualizada");
    redirectAttributes.addFlashAttribute("tipoMensaje", "success");
    return "redirect:/colecciones/gestion_colecciones";
  }

  private List<CriterioDTO> inicializarCriterios(ColeccionRequestDTO coleccion) {
    List<CriterioDTO> listaCriterios = new ArrayList<>();

    CriterioDTO critDesde = new CriterioDTO();
    critDesde.setTipo(TipoCriterio.FECHA_DESDE);
    listaCriterios.add(critDesde);

    CriterioDTO critHasta = new CriterioDTO();
    critHasta.setTipo(TipoCriterio.FECHA_HASTA);
    listaCriterios.add(critHasta);

    CriterioDTO critProv = new CriterioDTO();
    critProv.setTipo(TipoCriterio.PROVINCIA);
    listaCriterios.add(critProv);

    return listaCriterios;
  }

  private void cargarDatosFormulario(Model model) {
    model.addAttribute("categoriasDisponibles", categoriaService.obtenerCategorias());
    model.addAttribute("tiposDeConsenso", TipoConsenso.values());
    model.addAttribute("tiposDeFuentes", TipoFuente.values());
    model.addAttribute("titulo", "Crear Colección");
  }

}