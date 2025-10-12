package ar.utn.dssi.app_web.services;

import ar.utn.dssi.app_web.dto.input.HechoInputDTO;
import ar.utn.dssi.app_web.dto.output.HechoOutputDTO;
import ar.utn.dssi.app_web.error.UbicacionInvalida;
import ar.utn.dssi.app_web.error.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HechoServices {

  private final GestionHechosApiService gestionHechosApiService;

  public HechoOutputDTO crearHecho(HechoInputDTO hechoInputDTO) {
    validarDatosBasicos(hechoInputDTO);
    validarUbicacion(hechoInputDTO);
    return new HechoOutputDTO();
  }


  private void validarDatosBasicos(HechoInputDTO hechoInputDTO) {
    ValidationException validationException = new ValidationException("Errores de validación");
    boolean tieneErrores = false;

    if (hechoInputDTO.getTitulo() == null || hechoInputDTO.getTitulo().trim().isEmpty()) {
      validationException.addFieldError("titulo", "El titulo es obligatorio");
      tieneErrores = true;
    }

    if (hechoInputDTO.getDescripcion() == null || hechoInputDTO.getDescripcion().trim().isEmpty()) {
      validationException.addFieldError("descripcion", "La descripcion es obligatoria");
      tieneErrores = true;
    }

    if (hechoInputDTO.getFechaAcontecimiento() == null) {
      validationException.addFieldError("fecha acontecimiento", "La fecha es obligatoria");
      tieneErrores = true;
    }

    if (hechoInputDTO.getLatitud() == null) {
      validationException.addFieldError("latitud", "La latitud es obligatoria");
      tieneErrores = true;
    }

    if (hechoInputDTO.getLongitud() == null) {
      validationException.addFieldError("longitud", "La fecha es obligatoria");
      tieneErrores = true;
    }

    if (tieneErrores) {
      throw validationException;
    }
  }

  private void validarUbicacion(HechoInputDTO hechoInputDTO) {
    if (!gestionHechosApiService.ubicacionValida(hechoInputDTO.getLatitud(), hechoInputDTO.getLongitud())) {
      throw new UbicacionInvalida();
    }
  }
}
