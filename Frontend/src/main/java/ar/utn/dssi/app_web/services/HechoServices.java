package ar.utn.dssi.app_web.services;

import ar.utn.dssi.app_web.dto.EstadoHecho;
import ar.utn.dssi.app_web.dto.input.HechoRequest;
import ar.utn.dssi.app_web.dto.input.PageResponseDTO;
import ar.utn.dssi.app_web.dto.output.HechoOutputDTO;
import ar.utn.dssi.app_web.error.NotFoundException;
import ar.utn.dssi.app_web.error.UbicacionInvalida;
import ar.utn.dssi.app_web.error.ValidationException;
import ar.utn.dssi.app_web.services.Interfaces.IHechoService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HechoServices implements IHechoService {

  private final GestionHechosApiService gestionHechosApiService;

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
  public Boolean editarHecho(Long id, HechoRequest hechoRequest) {
    validarDatosBasicos(hechoRequest);
    validarUbicacion(hechoRequest);
    return gestionHechosApiService.editarHecho(id, hechoRequest);
  }

  @Override
  public PageResponseDTO<HechoOutputDTO> listarHechos() {
    return null;
  }

  @Override
  public void registrarSugerencia(Long id, String sugerencia) {

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
  public PageResponseDTO<HechoOutputDTO> listarHechos(int page, int size, String filtro, String sort) {
    // Validaciones
    int safePage = Math.max(0, page);
    int safeSize = size <= 0 ? 12 : Math.min(size, 100);

    // Llamada a la API
    PageResponseDTO<HechoOutputDTO> api =
            gestionHechosApiService.buscarProximosHechosAPaginar(safePage, safeSize, filtro, sort);

    // Si la API externa usa paginación 1-based pero tu frontend usa 0-based:
    // api.setNumber(api.getNumber() - 1);

    return api;
  }

  @Override //TODO
  public PageResponseDTO<HechoOutputDTO> listarHechosDeColeccion(long coleccionId) {
    return null;
  }
}
