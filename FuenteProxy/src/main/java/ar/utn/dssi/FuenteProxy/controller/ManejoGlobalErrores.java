package ar.utn.dssi.FuenteProxy.controller;

import ar.utn.dssi.FuenteProxy.dto.output.ErrorDTO;
import ar.utn.dssi.FuenteProxy.error.DatosFaltantes;
import ar.utn.dssi.FuenteProxy.error.ErrorGeneralRepositorio;
import ar.utn.dssi.FuenteProxy.error.FechaUltimaComunicacionFutura;
import ar.utn.dssi.FuenteProxy.error.HechoNormalizadoNoObtenido;
import ar.utn.dssi.FuenteProxy.error.RepositorioVacio;
import org.apache.coyote.Response;
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

    @ExceptionHandler(HechoNormalizadoNoObtenido.class)
    public ResponseEntity<ErrorDTO> hechoNormalizadoNoObtenido(HechoNormalizadoNoObtenido ex) {
        return construirRespuestaError(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FechaUltimaComunicacionFutura.class)
    public ResponseEntity<ErrorDTO> fechaUltimaComunicacionFutura(FechaUltimaComunicacionFutura ex) {
        return construirRespuestaError(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorDTO> construirRespuestaError(String mensaje, HttpStatus status) {
        ErrorDTO error = new ErrorDTO(mensaje, status.toString(), LocalDateTime.now());
        return ResponseEntity.status(status).body(error);
    }
}
