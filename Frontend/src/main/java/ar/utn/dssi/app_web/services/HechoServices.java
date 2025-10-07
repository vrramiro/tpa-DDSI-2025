package ar.utn.dssi.app_web.services;

import ar.utn.dssi.app_web.DTO.HechoDTO;
import ar.utn.dssi.app_web.exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HechoServices {

    public HechoDTO crearHecho(HechoDTO hechoDTO) {

    }



    private void validarDatosBasicos(HechoDTO hechoDTO) {
        ValidationException validationException = new ValidationException("Errores de validación");
        boolean tieneErrores = false;

        if( hechoDTO.getTitulo() == null || hechoDTO.getTitulo().trim().isEmpty()) {
            validationException.addFieldError("titulo", "El titulo es obligatorio");
            tieneErrores = true;
        }

        if( hechoDTO.getDescripcion() == null || hechoDTO.getDescripcion().trim().isEmpty()) {
            validationException.addFieldError("descripcion", "La descripcion es obligatoria");
            tieneErrores = true;
        }

        if( hechoDTO.getCategoria() == null || hechoDTO.getCategoria().trim().isEmpty()) {
            validationException.addFieldError("categoria", "La categoria es obligatoria");
            tieneErrores = true;
        }


    }
}
