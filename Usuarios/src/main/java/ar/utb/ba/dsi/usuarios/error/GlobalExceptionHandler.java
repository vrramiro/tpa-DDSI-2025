package ar.utb.ba.dsi.usuarios.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

public class GlobalExceptionHandler {

    @ExceptionHandler(UsuarioDuplicadoExcepcion.class)
    public ResponseEntity<Map<String, String>> handleUsuarioDuplicado(UsuarioDuplicadoExcepcion ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT); // 409 Conflict
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    //Captura la excepción de usuario no encontrado y devuelve un error HTTP 404 Not Found.
    @ExceptionHandler(UsuarioNoEncontrado.class)
    public ResponseEntity<Map<String, String>> handleUsuarioNoEncontrado(UsuarioNoEncontrado ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND); // 404 Not Found
    }

    //Captura la excepción de contraseña incorrecta y devuelve un error HTTP 401 Unauthorized.
    @ExceptionHandler(UsuarioContraseniaIncorrecta.class)
    public ResponseEntity<Map<String, String>> handleUsuarioContraseniaIncorrecta(UsuarioContraseniaIncorrecta ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED); // 401 Unauthorized
    }

    //Captura tu excepción personalizada para datos faltantes y devuelve un error HTTP 400 Bad Request.
    @ExceptionHandler(UsuarioDatosFaltantes.class)
    public ResponseEntity<Map<String, String>> handleUsuarioDatosFaltantes(UsuarioDatosFaltantes ex) {
        Map<String, String> errorResponse = ex.getFieldErrors();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST); // 400 Bad Request
    }
}
