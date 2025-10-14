package ar.utn.dssi.FuenteDinamica.controllers;

import ar.utn.dssi.FuenteDinamica.dto.output.ErrorDTO;
import ar.utn.dssi.FuenteDinamica.error.ArchivoMultimediaVacio;
import ar.utn.dssi.FuenteDinamica.error.DatosFaltantes;
import ar.utn.dssi.FuenteDinamica.error.DirectorioNoCreado;
import ar.utn.dssi.FuenteDinamica.error.ErrorGeneralRepositorio;
import ar.utn.dssi.FuenteDinamica.error.FallaGuardadoArchivoMultimedia;
import ar.utn.dssi.FuenteDinamica.error.HechoNoEditable;
import ar.utn.dssi.FuenteDinamica.error.RepositorioVacio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;

@RestControllerAdvice
public class ManejoGlobalErrores {

  @ExceptionHandler(DatosFaltantes.class)
  public ResponseEntity<ErrorDTO> datosFaltantes(DatosFaltantes ex) {
    return construirRespuestaError(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(RepositorioVacio.class)
  public ResponseEntity<ErrorDTO> repositorioVacio(RepositorioVacio ex) {
    return construirRespuestaError(ex.getMessage(), HttpStatus.NO_CONTENT);
  }

  @ExceptionHandler(ErrorGeneralRepositorio.class)
  public ResponseEntity<ErrorDTO> errorGeneralRepositorio(ErrorGeneralRepositorio ex) {
    return construirRespuestaError(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(HechoNoEditable.class)
  public ResponseEntity<ErrorDTO> hechoNoEditable(HechoNoEditable ex) {
    return construirRespuestaError(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(DirectorioNoCreado.class)
  public ResponseEntity<ErrorDTO> directorioNoCreado(DirectorioNoCreado ex) {
    return construirRespuestaError(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ArchivoMultimediaVacio.class)
  public ResponseEntity<ErrorDTO> archivoMultimediaVacio(ArchivoMultimediaVacio ex) {
    return construirRespuestaError(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(FallaGuardadoArchivoMultimedia.class)
  public ResponseEntity<ErrorDTO> FallaGuardadoArchivoMultimedia(FallaGuardadoArchivoMultimedia ex) {
    return construirRespuestaError(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private ResponseEntity<ErrorDTO> construirRespuestaError(String mensaje, HttpStatus status) {
    ErrorDTO error = new ErrorDTO(mensaje, status.toString(), LocalDateTime.now());
    return ResponseEntity.status(status).body(error);
  }
}
