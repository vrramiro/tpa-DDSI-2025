package ar.utn.dssi.Agregador.models.entities.algoritmoConsenso.impl;


import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.algoritmoConsenso.ComparadorDeTexto;
import ar.utn.dssi.Agregador.models.entities.algoritmoConsenso.IAlgoritmoConsenso;
import ar.utn.dssi.Agregador.models.entities.algoritmoConsenso.TipoConsenso;
import ar.utn.dssi.Agregador.models.entities.fuente.Fuente;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

@Component
public class MultiplesMenciones extends IAlgoritmoConsenso {
  private final ComparadorDeTexto comparadorDeTexto;

  public MultiplesMenciones() {
    this.comparadorDeTexto = new ComparadorDeTexto();
  }

  @Override
  protected TipoConsenso getTipoConsenso() {
    return TipoConsenso.MULTIPLES_MENCIONES;
  }

  @Override
  protected boolean cumplenCriterio(List<Hecho> hechosActuales, List<Fuente> fuentes, Map<String, List<Hecho>> hechosPorClave) {
    Integer cantidadHechosFuenteDistinta = hechosActuales.stream()
        .map(Hecho::getFuente)
        .distinct()
        .toList()
        .size();

    if (cantidadHechosFuenteDistinta < 2) return false;

    String claveActual = hechosActuales.stream().findAny().get().getClaveComparacion();

    for (Map.Entry<String, List<Hecho>> entrada : hechosPorClave.entrySet()) {
      String claveDistinta = entrada.getKey();

      if (claveDistinta.equals(claveActual)) continue;

      List<Hecho> hechosDistintos = entrada.getValue();

      for (Hecho hechoDistinto : hechosDistintos) {
        if (hechosActuales.stream().anyMatch(hechoActual ->
            this.comparadorDeTexto.cadenasSimilares(
                hechoActual.getTituloSanitizado(),
                hechoDistinto.getTituloSanitizado()
            )
        )) {
          return true;
        }
      }
    }

    return false;
  }
}
