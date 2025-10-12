package ar.utn.dssi.Agregador.controller;

import ar.utn.dssi.Agregador.error.ColeccionAguardandoActualizacion;
import ar.utn.dssi.Agregador.error.ColeccionNoEncontrada;
import ar.utn.dssi.Agregador.error.ColeccionTituloDuplicado;
import ar.utn.dssi.Agregador.error.CriterioDistintoTipo;
import ar.utn.dssi.Agregador.error.CriterioPorFechasIncorrecto;
import ar.utn.dssi.Agregador.error.CriterioTipoIncorrecto;
import ar.utn.dssi.Agregador.error.DatosDeColeccionFaltantes;
import ar.utn.dssi.Agregador.error.HechoNoEcontrado;
import ar.utn.dssi.Agregador.error.SolicitudDescripcionMuyCorta;
import ar.utn.dssi.Agregador.error.SolicitudNoEncontrada;
import ar.utn.dssi.Agregador.error.SolicitudYaProcesada;
import ar.utn.dssi.Agregador.models.DTOs.outputDTO.ErrorDTO;
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

  @ExceptionHandler(SolicitudNoEncontrada.class)
  public ResponseEntity<ErrorDTO> handleSolicitudNoEncontrada(SolicitudNoEncontrada ex) {
    return handleException(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(SolicitudYaProcesada.class)
  public ResponseEntity<ErrorDTO> handleSolicitudYaProcesada(SolicitudYaProcesada ex) {
    return handleException(ex.getMessage(), HttpStatus.CONFLICT);
  }

  @ExceptionHandler(SolicitudDescripcionMuyCorta.class)
  public ResponseEntity<ErrorDTO> handleSolicitudDescripcionMuyCorta(SolicitudDescripcionMuyCorta ex) {
    return handleException(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(HechoNoEcontrado.class)
  public ResponseEntity<ErrorDTO> handleHechoNoEncontrado(HechoNoEcontrado ex) {
    return handleException(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(CriterioTipoIncorrecto.class)
  public ResponseEntity<ErrorDTO> handleCriterioTipoIncorrecto(CriterioTipoIncorrecto ex) {
    return handleException(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(CriterioPorFechasIncorrecto.class)
  public ResponseEntity<ErrorDTO> hendleCriterioPorFechasIncorrecto(CriterioPorFechasIncorrecto ex) {
    return handleException(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ColeccionTituloDuplicado.class)
  public ResponseEntity<ErrorDTO> handleColeccionTituloDuplicado(ColeccionTituloDuplicado ex) {
    return handleException(ex.getMessage(), HttpStatus.CONFLICT);
  }

  public ResponseEntity<ErrorDTO> handleException(String mensaje, HttpStatus status) {
    ErrorDTO errorDTO = new ErrorDTO(mensaje, status.toString(), LocalDateTime.now());
    return ResponseEntity.status(status).body(errorDTO);
  }
}
