package ar.utn.dssi.Agregador.models.entities.algoritmoConsenso;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class ComparadorDeTexto {
  @Value("${comparador.jaro-winkler-threshold}")
  private double jaroWinklerThreshold;

  @Value("${comparador.jaccard-threshold}")
  private double jaccardThreshold;

  @Value("${comparador.min-token-len}")
  private int minTokenLen;

  private static double jaccard(Set<String> A, Set<String> B) {
    if (A.isEmpty() || B.isEmpty()) return 0.0;

    Set<String> interseccion = new HashSet<>(A);
    interseccion.retainAll(B);

    Set<String> union = new HashSet<>(A);
    union.addAll(B);

    return (double) interseccion.size() / (double) union.size();
  }

  private static double jaroWinkler(String s1, String s2) {
    double jaro = jaroSimilarity(s1, s2);

    int prefix = 0;
    int maxPrefix = 4;
    int limit = Math.min(Math.min(s1.length(), s2.length()), maxPrefix);

    for (int i = 0; i < limit; i++) {
      if (s1.charAt(i) == s2.charAt(i)) prefix++;
      else break;
    }

    double p = 0.1; // scaling factor estándar
    return jaro + (prefix * p * (1.0 - jaro));
  }

  private static double jaroSimilarity(String cadena1, String cadena2) {
    if (cadena1.equals(cadena2)) return 1.0;

    int largo1 = cadena1.length();
    int largo2 = cadena2.length();
    if (largo1 == 0 || largo2 == 0) return 0.0;

    int matchDistance = Math.max(largo1, largo2) / 2 - 1;

    boolean[] s1Matches = new boolean[largo1];
    boolean[] s2Matches = new boolean[largo2];

    int matches = 0;

    for (int i = 0; i < largo1; i++) {
      int start = Math.max(0, i - matchDistance);
      int end = Math.min(i + matchDistance + 1, largo2);

      for (int j = start; j < end; j++) {
        if (s2Matches[j]) continue;
        if (cadena1.charAt(i) != cadena2.charAt(j)) continue;
        s1Matches[i] = true;
        s2Matches[j] = true;
        matches++;
        break;
      }
    }

    if (matches == 0) return 0.0;

    int t = 0;
    int k = 0;

    for (int i = 0; i < largo1; i++) {
      if (!s1Matches[i]) continue;
      while (k < largo2 && !s2Matches[k]) k++;
      if (k < largo2 && cadena1.charAt(i) != cadena2.charAt(k)) t++;
      k++;
    }

    double transpositions = t / 2.0;

    return (
        (matches / (double) largo1) +
            (matches / (double) largo2) +
            ((matches - transpositions) / (double) matches)
    ) / 3.0;
  }

  private Set<String> tokenize(String s) {
    if (s.isEmpty()) return Collections.emptySet();

    String[] parts = s.split("\\s+");
    Set<String> out = new HashSet<>(parts.length);

    for (String p : parts) {
      if (p == null) continue;
      String t = p.trim();
      if (t.length() >= minTokenLen) out.add(t);
    }
    return out;
  }

  public boolean cadenasSimilares(String titulo1, String titulo2) {
    if (titulo1 == null || titulo2 == null) return false;

    String a = titulo1.trim();
    String b = titulo2.trim();

    if (a.isEmpty() || b.isEmpty()) return false;
    if (a.equals(b)) return true;

    double jw = jaroWinkler(a, b);
    if (jw >= jaroWinklerThreshold) return true;

    double jac = jaccard(tokenize(a), tokenize(b));
    return jac >= jaccardThreshold;
  }
}
