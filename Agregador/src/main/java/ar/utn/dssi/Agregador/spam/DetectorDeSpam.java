package ar.utn.dssi.Agregador.spam;

import ar.utn.dssi.Agregador.models.entities.solicitud.SolicitudDeEliminacion;
import ar.utn.dssi.Agregador.models.repositories.ISolicitudDeEliminacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

public class DetectorDeSpam {

  private final ISolicitudDeEliminacionRepository solicitudRepo;

  // Umbral ajustable
  private double UMBRAL = 0.5;

  public DetectorDeSpam(ISolicitudDeEliminacionRepository solicitudRepo) {
    this.solicitudRepo = solicitudRepo;
  }

  public static boolean esSpam(String descripcion) {
    if (descripcion == null || descripcion.isBlank()) return false;

    // Obtener corpus: descripciones anteriores
    List<String> corpus = solicitudRepo.findAll().stream()
        .map(SolicitudDeEliminacion::getDescripcion)
        .filter(Objects::nonNull)
        .collect(Collectors.toList());

    if (corpus.isEmpty()) return false; // No hay datos previos para comparar

    // Calcular IDF
    Map<String, Double> idf = calcularIDF(corpus);

    // Calcular TF del nuevo texto
    Map<String, Double> tfNuevo = calcularTF(descripcion);

    // Calcular score TF-IDF del nuevo texto
    double score = tfNuevo.entrySet().stream()
        .mapToDouble(e -> e.getValue() * idf.getOrDefault(e.getKey(), 0.0))
        .sum();

    return score >= UMBRAL;
  }

  private Map<String, Double> calcularTF(String texto) {
    String[] palabras = texto.toLowerCase().split("\\W+");
    Map<String, Long> frecuencia = Arrays.stream(palabras)
        .collect(Collectors.groupingBy(p -> p, Collectors.counting()));
    long totalPalabras = palabras.length;

    Map<String, Double> tf = new HashMap<>();
    frecuencia.forEach((palabra, count) -> tf.put(palabra, count / (double) totalPalabras));
    return tf;
  }

  private Map<String, Double> calcularIDF(List<String> documentos) {
    Map<String, Integer> docCount = new HashMap<>();
    int totalDocs = documentos.size();

    for (String doc : documentos) {
      Set<String> palabrasUnicas = new HashSet<>(Arrays.asList(doc.toLowerCase().split("\\W+")));
      for (String palabra : palabrasUnicas) {
        docCount.put(palabra, docCount.getOrDefault(palabra, 0) + 1);
      }
    }

    Map<String, Double> idf = new HashMap<>();
    docCount.forEach((palabra, count) ->
        idf.put(palabra, Math.log((double) totalDocs / (1 + count)))
    );

    return idf;
  }
}



