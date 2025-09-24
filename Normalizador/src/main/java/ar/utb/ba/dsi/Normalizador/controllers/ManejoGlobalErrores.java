package ar.utb.ba.dsi.Normalizador.controllers;

import ar.utb.ba.dsi.Normalizador.models.DTOs.Output.ErrorDTO;
import ar.utb.ba.dsi.Normalizador.models.entities.errores.*;
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

    @ExceptionHandler(CategoriaNotFoundException.class)
    public ResponseEntity<ErrorDTO> categoriaNotFound(CategoriaNotFoundException ex) {
        return construirRespuestaError(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoEncontrado.class)
    public ResponseEntity<ErrorDTO> noEncontrado(NoEncontrado ex) {
        return construirRespuestaError(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<ErrorDTO> construirRespuestaError(String mensaje, HttpStatus status) {
        ErrorDTO error = new ErrorDTO(mensaje, status.toString(), LocalDateTime.now());
        return ResponseEntity.status(status).body(error);
    }

}
