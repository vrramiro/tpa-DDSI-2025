package ar.utn.dssi.Agregador.models.entities.algoritmoConsenso;

import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.fuente.Fuente;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class IAlgoritmoConsenso {
  public void consensuar(List<Hecho> hechos, List<Fuente> fuentes) {
    Map<String, List<Hecho>> hechosPorClave = agruparHechosPorClave(hechos);

    hechosPorClave.forEach((clave, lista) -> {
      if (this.cumplenCriterio(lista, fuentes, hechosPorClave)) {
        lista.forEach(hecho -> hecho.agregarConsenso(this.getTipoConsenso()));
      }
    });
  }

  protected HashMap<String, List<Hecho>> agruparHechosPorClave(List<Hecho> hechos) {
    HashMap<String, List<Hecho>> hechosPorClave = new HashMap<>();

    for (Hecho hecho : hechos) {
      String clave = hecho.getClaveComparacion();
      hechosPorClave.computeIfAbsent(clave, k -> new ArrayList<>()).add(hecho);
    }

    return hechosPorClave;
  }

  protected abstract TipoConsenso getTipoConsenso();

  protected abstract boolean cumplenCriterio(List<Hecho> hechosActuales, List<Fuente> fuentes, Map<String, List<Hecho>> hechosPorClave);
}

