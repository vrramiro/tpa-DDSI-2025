package ar.utn.dssi.FuenteEstatica.controllers;

import ar.utn.dssi.FuenteEstatica.models.DTOs.output.ErrorDTO;
import ar.utn.dssi.FuenteEstatica.models.errores.ErrorActualizarRepositorio;
import ar.utn.dssi.FuenteEstatica.models.errores.ErrorGeneralRepositorio;
import ar.utn.dssi.FuenteEstatica.models.errores.RepositorioVacio;
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
    return construirRespuestaError(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ErrorActualizarRepositorio.class)
  public ResponseEntity<ErrorDTO> manejarActualizarRepositorio(ErrorActualizarRepositorio ex) {
    return construirRespuestaError(ex.getMessage(), ex.getStatus());
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
