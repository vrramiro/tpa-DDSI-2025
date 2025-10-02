package ar.utb.ba.dsi.Normalizador.models.entities.sanitizador;

import ar.utb.ba.dsi.Normalizador.models.entities.Hecho;
import org.springframework.stereotype.Component;
import java.text.Normalizer;

@Component
public class Sanitizador {
  public static void sanitizar(Hecho hecho) {
    if (hecho.getTitulo() != null) {
      hecho.setTituloSanitizado(aplanarTexto(hecho.getTitulo()));
    }
    if (hecho.getDescripcion() != null) {
      hecho.setDescripcionSanitizada(aplanarTexto(hecho.getDescripcion()));
    }
  }

  private static String aplanarTexto(String texto) {
    String normalizado = texto.toLowerCase();

    // Elimina acentos
    normalizado = Normalizer.normalize(normalizado, Normalizer.Form.NFD)
            .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

    // Eliminar caracteres que no sean letras, números o espacios
    normalizado = normalizado.replaceAll("[^a-z0-9\\s]", "");

    // Quitar espacios extra
    normalizado = normalizado.trim().replaceAll("\\s+", " ");

    return normalizado;
  }

}
