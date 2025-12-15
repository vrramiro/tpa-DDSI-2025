package ar.utn.dssi.Agregador.models.entities.algoritmoConsenso.impl;

import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.algoritmoConsenso.IAlgoritmoConsenso;
import ar.utn.dssi.Agregador.models.entities.algoritmoConsenso.TipoConsenso;
import ar.utn.dssi.Agregador.models.entities.fuente.Fuente;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

@Component
public class MayoriaSimple extends IAlgoritmoConsenso {
  @Override
  protected TipoConsenso getTipoConsenso() {
    return TipoConsenso.MAYORIA_SIMPLE;
  }

  @Override
  protected boolean cumplenCriterio(List<Hecho> hechosActuales, List<Fuente> fuentes, Map<String, List<Hecho>> hechosPorClave) {
    Integer cantidadFuentes = fuentes.size();
    Integer cantidadHechosFuenteDistinta = hechosActuales.stream()
        .map(Hecho::getFuente)
        .distinct()
        .toList()
        .size();
    
    return cantidadHechosFuenteDistinta >= cantidadFuentes / 2;
  }
}

