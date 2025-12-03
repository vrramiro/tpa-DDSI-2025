package ar.utn.dssi.app_web.services.impl;

import ar.utn.dssi.app_web.dto.EstadoHecho;
import ar.utn.dssi.app_web.dto.input.HechoRequest;
import ar.utn.dssi.app_web.dto.input.PageResponseDTO;
import ar.utn.dssi.app_web.dto.output.HechoOutputDTO;
import ar.utn.dssi.app_web.dto.output.SolicitudEdicionDTO;
import ar.utn.dssi.app_web.error.NotFoundException;
import ar.utn.dssi.app_web.error.UbicacionInvalida;
import ar.utn.dssi.app_web.error.ValidationException;
import ar.utn.dssi.app_web.services.GestionHechosApiService;
import ar.utn.dssi.app_web.services.Interfaces.IHechoService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class HechoServices implements IHechoService {

  private final GestionHechosApiService gestionHechosApiService;

  private static final List<String> NOMBRES_PROVINCIAS = Arrays.asList(
          "Buenos Aires",
          "Catamarca",
          "Chaco",
          "Chubut",
          "Ciudad Autónoma de Buenos Aires",
          "Córdoba",
          "Corrientes",
          "Entre Ríos",
          "Formosa",
          "Jujuy",
          "La Pampa",
          "La Rioja",
          "Mendoza",
          "Misiones",
          "Neuquén",
          "Río Negro",
          "Salta",
          "San Juan",
          "San Luis",
          "Santa Cruz",
          "Santa Fe",
          "Santiago del Estero",
          "Tierra del Fuego, Antártida e Islas del Atlántico Sur",
          "Tucumán"
  );

  public HechoServices(GestionHechosApiService gestionHechosApiService) {
    this.gestionHechosApiService = gestionHechosApiService;
  }

  @Override
  public Boolean crearHecho(HechoRequest hechoRequest) {
    validarDatosBasicos(hechoRequest);
    validarUbicacion(hechoRequest);
    return gestionHechosApiService.crearHecho(hechoRequest);
  }

  @Override
    public Boolean crearHechoEstatico(MultipartFile archivo) {
    System.out.println("entre a crear estatico importar");

    return gestionHechosApiService.crearHechoEstatica(archivo);
    }

  @Override
  public Optional<HechoOutputDTO> obtenerHechoPorId(Long id) {
    try {
      HechoOutputDTO hecho = gestionHechosApiService.obtenerHechoPorId(id);
      return Optional.of(hecho);
    } catch (NotFoundException e) {
      return Optional.empty();
    }
  }

  @Override
  public void cambiarEstadoHecho(Long id, EstadoHecho nuevoEstado) {
    gestionHechosApiService.cambiarEstadoHecho(id, nuevoEstado);
  }

  @Override
  public Boolean crearSolicitudEdicion(Long idHecho, HechoRequest nuevosDatos) {
    if (nuevosDatos.getIdCategoria() == null) {
      throw new ValidationException("La categoría es obligatoria");
    }
    return gestionHechosApiService.crearSolicitudEdicion(idHecho, nuevosDatos);
  }

  private void validarDatosBasicos(HechoRequest hechoRequest) {
    ValidationException validationException = new ValidationException("Errores de validación");
    boolean tieneErrores = false;

    if( hechoRequest.getTitulo() == null || hechoRequest.getTitulo().trim().isEmpty()) {
      validationException.addFieldError("titulo", "El titulo es obligatorio");
      tieneErrores = true;
    }

    if( hechoRequest.getDescripcion() == null || hechoRequest.getDescripcion().trim().isEmpty()) {
      validationException.addFieldError("descripcion", "La descripcion es obligatoria");
      tieneErrores = true;
    }

    if(hechoRequest.getFechaAcontecimiento() == null) {
      validationException.addFieldError("fechaAcontecimiento", "La fecha es obligatoria");
      tieneErrores = true;
    }

    if(hechoRequest.getIdCategoria() == null ) {
      validationException.addFieldError("idCategoria", "Debe seleccionar una categoría");
      tieneErrores = true;
    }

    if(hechoRequest.getLatitud() == null || hechoRequest.getLongitud() == null) {
      validationException.addFieldError("latitud", "La ubicacion es obligatoria");
      validationException.addFieldError("longitud", "La ubicacion es obligatoria");
      tieneErrores = true;
    }

    if(tieneErrores) {
      throw validationException;
    }
  }

  private void validarUbicacion(HechoRequest hechoRequest){
    if(!gestionHechosApiService.ubicacionValida(hechoRequest.getLatitud(), hechoRequest.getLongitud())) {
      throw new UbicacionInvalida();
    }
  }

  @Override
  public PageResponseDTO<HechoOutputDTO> listarHechos(Integer page, Integer size, String estado) {
    return gestionHechosApiService.buscarProximosHechosAPaginar(page, size, estado);
  }

  @Override //TODO
  public PageResponseDTO<HechoOutputDTO> listarHechosDeColeccion(String handle) {
    return null;
  }

  @Override
  public List<HechoOutputDTO> obtenerHechos(LocalDate fechaAcontecimientoDesde, LocalDate fechaAcontecimientoHasta, Long idCategoria, String provincia) {
    return gestionHechosApiService.obtenerHechos(null, null,
            fechaAcontecimientoDesde, fechaAcontecimientoHasta,
            idCategoria, provincia);
  }

  @Override
  public List<String> obtenerProvincias() {
    return NOMBRES_PROVINCIAS;
  }

  @Override
  public List<HechoOutputDTO> obtenerMisHechos() {
    return gestionHechosApiService.obtenerMisHechos();
  }

  @Override
  public List<SolicitudEdicionDTO> obtenerSolicitudesEdicionPendientes() {
    return gestionHechosApiService.obtenerSolicitudesEdicionPendientes();
  }

  @Override
  public void procesarSolicitudEdicion(Long id, String accion) {
    gestionHechosApiService.procesarSolicitudEdicion(id, accion);
  }

  @Override
  public Optional<SolicitudEdicionDTO> obtenerSolicitudEdicionPorId(Long id) {
    return obtenerSolicitudesEdicionPendientes().stream()
            .filter(s -> s.getId().equals(id))
            .findFirst();
  }
}
