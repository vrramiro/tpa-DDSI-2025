package ar.utn.dssi.FuenteEstatica.controllers;

import ar.utn.dssi.FuenteEstatica.models.DTOs.output.ErrorDTO;
import ar.utn.dssi.FuenteEstatica.models.errores.ErrorActualizarRepositorio;
import ar.utn.dssi.FuenteEstatica.models.errores.ValidacionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ManejoGlobalErrores {

    @ExceptionHandler(ValidacionException.class)
    public ResponseEntity<ErrorDTO> manejarValidacion(ValidacionException ex) {
        ErrorDTO error = new ErrorDTO(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.toString(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(ErrorActualizarRepositorio.class)
    public ResponseEntity<ErrorDTO> manejarActualizarRepositorio(ErrorActualizarRepositorio ex) {
        ErrorDTO error = new ErrorDTO(
                ex.getMessage(),
                ex.getStatus().toString(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(400).body(error);
    }

}
