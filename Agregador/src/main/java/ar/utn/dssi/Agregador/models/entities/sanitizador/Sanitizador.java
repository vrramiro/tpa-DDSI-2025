package ar.utn.dssi.Agregador.models.entities.sanitizador;

import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.HechoSanitizado;
import org.springframework.stereotype.Component;
import java.text.Normalizer;
import java.util.List;

@Component
public class Sanitizador {
  public void sanitizar(List<Hecho> hechos) {
    //TODO
  }

  private String aplanarTexto(String texto) {
    String normalizado = texto.toLowerCase();

    normalizado = Normalizer.normalize(normalizado, Normalizer.Form.NFD).replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

    normalizado = normalizado.replaceAll("[^a-z0-9\\s]", "");

    normalizado = normalizado.trim().replaceAll("\\s+", " ");

    return normalizado;
  }
}
