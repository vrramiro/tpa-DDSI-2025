package ar.utn.dssi.Agregador.models.entities.algoritmoConsenso.impl;


import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.algoritmoConsenso.IAlgoritmoConsenso;
import ar.utn.dssi.Agregador.models.entities.algoritmoConsenso.TipoConsenso;
import ar.utn.dssi.Agregador.models.entities.fuente.Fuente;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MultiplesMenciones extends IAlgoritmoConsenso {
  @Override
  public void consensuar(List<Hecho> hechos, List<Fuente> fuentes) {
    Integer cantidadFuentes = fuentes.size();

    Map<String, List<Hecho>> hechosPorClave = agruparHechosPorClave(hechos);

    hechosPorClave.forEach((clave, listaHechos) -> {
      List<Fuente> fuentesDeMencion = listaHechos.stream().map(Hecho::getFuente).collect(Collectors.toList());
      if (fuentesDeMencion.stream().distinct().count() == cantidadFuentes) {
        listaHechos.forEach(hecho -> hecho.agregarConsenso(TipoConsenso.ABSOLUTA));
      }
    });
  }
}
