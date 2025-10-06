package ar.utn.dssi.Agregador.controller;

import ar.utn.dssi.Agregador.error.ColeccionAguardandoActualizacion;
import ar.utn.dssi.Agregador.error.ColeccionNoEncontrada;
import ar.utn.dssi.Agregador.error.CriterioDistintoTipo;
import ar.utn.dssi.Agregador.models.DTOs.outputDTO.ErrorDTO;
import ar.utn.dssi.Agregador.error.DatosDeColeccionFaltantes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GeneralExceptionHandler {
  @ExceptionHandler(DatosDeColeccionFaltantes.class)
  public ResponseEntity<ErrorDTO> handleTituloFaltante(DatosDeColeccionFaltantes ex) {
    return handleException(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ColeccionAguardandoActualizacion.class)
  public ResponseEntity<ErrorDTO> handleColeccionAguardandoActualizacion(ColeccionAguardandoActualizacion ex) {
    return handleException(ex.getMessage(), HttpStatus.CONFLICT);
  }

  @ExceptionHandler(ColeccionNoEncontrada.class)
  public ResponseEntity<ErrorDTO> handleColeccionNoEncontrada(ColeccionNoEncontrada ex) {
    return handleException(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(CriterioDistintoTipo.class)
  public ResponseEntity<ErrorDTO> handleCriterioDistintoTipo(CriterioDistintoTipo ex) {
    return handleException(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  public ResponseEntity<ErrorDTO> handleException(String mensaje, HttpStatus status) {
    ErrorDTO errorDTO = new ErrorDTO(mensaje, status.toString(), LocalDateTime.now());
    return ResponseEntity.status(status).body(errorDTO);
  }
}
