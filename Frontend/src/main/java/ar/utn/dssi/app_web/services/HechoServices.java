package ar.utn.dssi.app_web.services;

import ar.utn.dssi.app_web.dto.EstadoHecho;
import ar.utn.dssi.app_web.dto.input.HechoInputDTO;
import ar.utn.dssi.app_web.dto.input.PageResponseDTO;
import ar.utn.dssi.app_web.dto.output.HechoOutputDTO;
import ar.utn.dssi.app_web.error.NotFoundException;
import ar.utn.dssi.app_web.error.UbicacionInvalida;
import ar.utn.dssi.app_web.error.ValidationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HechoServices {

  private final GestionHechosApiService gestionHechosApiService;

  public HechoServices(GestionHechosApiService gestionHechosApiService) {
    this.gestionHechosApiService = gestionHechosApiService;
  }

  public Boolean crearHecho(HechoInputDTO hechoInputDTO) {
    validarDatosBasicos(hechoInputDTO);
    validarUbicacion(hechoInputDTO);
    return gestionHechosApiService.crearHecho(hechoInputDTO);
  }

  public Optional<HechoOutputDTO> obtenerHechoPorId(Long id) {
    try {
      HechoOutputDTO hecho = gestionHechosApiService.obtenerHechoPorId(id);
      return Optional.of(hecho);
    } catch (NotFoundException e) {
      return Optional.empty();
    }
  }

  public void cambiarEstadoHecho(Long id, EstadoHecho nuevoEstado) {
    gestionHechosApiService.cambiarEstadoHecho(id, nuevoEstado);
  }

  public Boolean editarHecho(Long id, HechoInputDTO hechoInputDTO) {
    validarDatosBasicos(hechoInputDTO);
    validarUbicacion(hechoInputDTO);
    return gestionHechosApiService.editarHecho(id, hechoInputDTO);
  }

/**********************************************************************************************************************/
/*************************************************LO DE ABAJO ES EXTRA*************************************************/
  /**********************************************************************************************************************/


  private void validarDatosBasicos(HechoInputDTO hechoInputDTO) {
    ValidationException validationException = new ValidationException("Errores de validación");
    boolean tieneErrores = false;

    if( hechoInputDTO.getTitulo() == null || hechoInputDTO.getTitulo().trim().isEmpty()) {
      validationException.addFieldError("titulo", "El titulo es obligatorio");
      tieneErrores = true;
    }

    if( hechoInputDTO.getDescripcion() == null || hechoInputDTO.getDescripcion().trim().isEmpty()) {
      validationException.addFieldError("descripcion", "La descripcion es obligatoria");
      tieneErrores = true;
    }

    if(hechoInputDTO.getFechaAcontecimiento() == null) {
      validationException.addFieldError("fechaAcontecimiento", "La fecha es obligatoria");
      tieneErrores = true;
    }

    if(hechoInputDTO.getCategoria() == null || hechoInputDTO.getCategoria().getId() == null) {
      validationException.addFieldError("categoria", "Debe seleccionar una categoría");
      tieneErrores = true;
    }

    if(hechoInputDTO.getLatitud() == null) {
      validationException.addFieldError("latitud", "La latitud es obligatoria");
      tieneErrores = true;
    }

    if (hechoInputDTO.getLongitud() == null) {
      validationException.addFieldError("longitud", "La longitud es obligatoria");
      tieneErrores = true;
    }

    if(tieneErrores) {
      throw validationException;
    }
  }

  private void validarUbicacion(HechoInputDTO hechoInputDTO){
    if(!gestionHechosApiService.ubicacionValida(hechoInputDTO.getLatitud(), hechoInputDTO.getLongitud())) {
      throw new UbicacionInvalida();
    }
  }

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
}
