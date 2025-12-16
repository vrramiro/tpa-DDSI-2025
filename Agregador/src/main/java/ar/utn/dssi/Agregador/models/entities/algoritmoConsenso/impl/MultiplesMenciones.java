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

  public MultiplesMenciones(ComparadorDeTexto comparadorDeTexto) {
    this.comparadorDeTexto = comparadorDeTexto;
  }

  @Override
  protected TipoConsenso getTipoConsenso() {
    return TipoConsenso.MULTIPLES_MENCIONES;
  }

  @Override
  protected boolean cumplenCriterio(List<Hecho> hechosActuales, List<Fuente> fuentes, Map<String, List<Hecho>> hechosPorClave) {
    if (hechosActuales == null || hechosActuales.isEmpty()) return false;

    int cantidadHechosFuenteDistinta = hechosActuales.stream()
        .map(Hecho::getFuente)
        .distinct()
        .toList()
        .size();

    if (cantidadHechosFuenteDistinta < 2) return false;

    String claveActual = hechosActuales.get(0).getClaveComparacion();
    List<String> titulosActuales = hechosActuales.stream().map(Hecho::getTituloSanitizado).toList();

    for (Map.Entry<String, List<Hecho>> entrada : hechosPorClave.entrySet()) {
      String claveDistinta = entrada.getKey();

      if (claveDistinta.equals(claveActual)) continue;

      for (Hecho hechoDistinto : entrada.getValue()) {
        String tituloDistinto = hechoDistinto.getTituloSanitizado();

        if (titulosActuales.stream().anyMatch(titulo -> comparadorDeTexto.cadenasSimilares(titulo, tituloDistinto)))
          return false;
      }
    }

    return true;
  }
}
