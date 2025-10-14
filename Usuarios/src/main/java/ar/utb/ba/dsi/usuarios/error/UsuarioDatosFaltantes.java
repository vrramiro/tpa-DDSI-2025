package ar.utb.ba.dsi.usuarios.error;

import java.util.HashMap;
import java.util.Map;

public class UsuarioDatosFaltantes extends RuntimeException {

  private final Map<String, String> fieldErrors = new HashMap<>();

  public UsuarioDatosFaltantes(String message) {
    super(message);
  }
  public void addFieldError(String field, String error) {
    fieldErrors.put(field, error);
  }

  public Map<String, String> getFieldErrors() {
    return fieldErrors;
  }
}
