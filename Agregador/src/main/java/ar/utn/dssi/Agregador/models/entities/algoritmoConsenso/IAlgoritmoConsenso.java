package ar.utn.dssi.Agregador.models.entities.algoritmoConsenso;

import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.fuente.Fuente;
import java.util.HashMap;
import java.util.List;

public abstract class IAlgoritmoConsenso {
  public abstract void consensuar(List<Hecho> hechos, List<Fuente> fuentes);

  protected HashMap<String, List<Hecho>> agruparHechosPorClave(List<Hecho> hechos) {
    HashMap<String, List<Hecho>> hechosPorClave = new HashMap<>();

    for (Hecho hecho : hechos) {
      String clave = hecho.getClaveComparacion();
      List<Hecho> listaHechos = hechosPorClave.getOrDefault(clave, List.of());
      listaHechos.add(hecho);
      hechosPorClave.put(clave, listaHechos);
    }

    return hechosPorClave;
  }
}

