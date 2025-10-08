package ar.utn.dssi.app_web.services;

import ar.utn.dssi.app_web.DTO.input.HechoInputDTO;
import ar.utn.dssi.app_web.DTO.output.HechoOutputDTO;
import ar.utn.dssi.app_web.exceptions.ValidationException;
import org.springframework.stereotype.Service;

@Service
public class HechoServices {

    public HechoOutputDTO crearHecho(HechoInputDTO hechoInputDTO) {
        validarDatosBasicos(hechoInputDTO);
        return new HechoOutputDTO();
    }



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

        if(hechoInputDTO.getFechaAcontecimiento() == null || hechoInputDTO.getFechaAcontecimiento().trim().isEmpty()) {
            validationException.addFieldError("fecha acontecimiento", "La fecha es obligatoria");
            tieneErrores = true;
        }

        if(hechoInputDTO.getLatitud() == null || hechoInputDTO.getLatitud().trim().isEmpty()) {
            validationException.addFieldError("latitud", "La latitud es obligatoria");
            tieneErrores = true;
        }

        if (hechoInputDTO.getLongitud() == null || hechoInputDTO.getLongitud().trim().isEmpty()) {
            validationException.addFieldError("longitud", "La fecha es obligatoria");
            tieneErrores = true;
        }

        if(tieneErrores) {
            throw validationException;
        }

    }
}
