package ar.utn.dssi.FuenteDinamica.controllers;

import ar.utn.dssi.FuenteDinamica.models.DTOs.outputs.ErrorDTO;
import ar.utn.dssi.FuenteDinamica.models.errores.DatosFaltantes;
import ar.utn.dssi.FuenteDinamica.models.errores.ErrorGeneralRepositorio;
import ar.utn.dssi.FuenteDinamica.models.errores.RepositorioVacio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ManejoGlobalErrores {

    @ExceptionHandler(DatosFaltantes.class)
    public ResponseEntity<ErrorDTO> datosFaltantes(DatosFaltantes ex) {
        return construirRespuestaError(ex.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(RepositorioVacio.class)
    public ResponseEntity<ErrorDTO> repositorioVacio(RepositorioVacio ex) {
        return construirRespuestaError(ex.getMessage(), HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(ErrorGeneralRepositorio.class)
    public ResponseEntity<ErrorDTO> errorGeneralRepositorio(ErrorGeneralRepositorio ex) {
        return construirRespuestaError(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorDTO> construirRespuestaError(String mensaje, HttpStatus status) {
        ErrorDTO error = new ErrorDTO(mensaje, status.toString(), LocalDateTime.now());
        return ResponseEntity.status(status).body(error);
    }
}
