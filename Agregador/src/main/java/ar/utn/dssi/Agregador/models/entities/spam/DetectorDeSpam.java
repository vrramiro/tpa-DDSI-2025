package ar.utn.dssi.Agregador.models.entities.spam;

import java.util.Arrays;
import java.util.List;

public class DetectorDeSpam {

  private static final int MIN_LENGTH = 50;

  private static final List<String> PALABRAS_SPAM = Arrays.asList(
      "oferta", "gratis", "promoción", "click aquí", "haz clic", "compra ya", "dinero rápido"
  );

  public static Boolean esSpam(String descripcion) {
    if (descripcion == null || descripcion.isBlank()) {
      return false;
    }

    if (descripcion.length() < MIN_LENGTH) {
      return true;
    }

    String texto = descripcion.toLowerCase();

    for (String palabra : PALABRAS_SPAM) {
      if (texto.contains(palabra)) {
        return true;
      }
    }

    int mayus = 0;
    for (char c : descripcion.toCharArray()) {
      if (Character.isUpperCase(c)) {
        mayus++;
      }
    }
    if (descripcion.length() > 10 && (mayus * 100 / descripcion.length()) > 50) {
      return true;
    }

    if (texto.contains("!!!!") || texto.contains("????")) {
      return true;
    }

    return false;
  }
}
