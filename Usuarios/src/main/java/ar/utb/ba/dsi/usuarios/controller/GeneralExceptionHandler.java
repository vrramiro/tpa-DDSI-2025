package ar.utb.ba.dsi.usuarios.controller;

import ar.utb.ba.dsi.usuarios.dto.output.ErrorDTO;
import ar.utb.ba.dsi.usuarios.error.UsuarioContraseniaIncorrecta;
import ar.utb.ba.dsi.usuarios.error.UsuarioDatosFaltantes;
import ar.utb.ba.dsi.usuarios.error.UsuarioNoEncontrado;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GeneralExceptionHandler {
  @ExceptionHandler(UsuarioDatosFaltantes.class)
  public ResponseEntity<ErrorDTO> handleUsuarioDatosFaltantes(UsuarioDatosFaltantes ex) {
    return handleException(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(UsuarioNoEncontrado.class)
  public ResponseEntity<ErrorDTO> handleUsuarioNoEncontrado(UsuarioNoEncontrado ex) {
    return handleException(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(UsuarioContraseniaIncorrecta.class)
  public ResponseEntity<ErrorDTO> handleUsuarioContraseniaIncorrecta(UsuarioContraseniaIncorrecta ex) {
    return handleException(ex.getMessage(), HttpStatus.UNAUTHORIZED);
  }

  public ResponseEntity<ErrorDTO> handleException(String mensaje, HttpStatus status) {
    ErrorDTO errorDTO = new ErrorDTO(mensaje, status.toString(), LocalDateTime.now());
    return ResponseEntity.status(status).body(errorDTO);
  }
}
