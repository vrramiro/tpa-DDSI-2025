package ar.utn.dssi.Agregador.models.entities.consensuador.impl;

import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.algoritmoConsenso.IAlgoritmoConsenso;
import ar.utn.dssi.Agregador.models.entities.algoritmoConsenso.TipoConsenso;
import ar.utn.dssi.Agregador.models.entities.consensuador.IConsensuadorDeHechos;
import ar.utn.dssi.Agregador.models.entities.fuente.Fuente;
import ch.hsr.geohash.GeoHash;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ConsensuadorDeHechos implements IConsensuadorDeHechos {
  private final List<IAlgoritmoConsenso> algoritmosConsenso;

  public ConsensuadorDeHechos(List<IAlgoritmoConsenso> algoritmosConsenso) {
    this.algoritmosConsenso = algoritmosConsenso;
  }

  public void consensuar(List<Hecho> hechos, List<Fuente> fuentes) {
    hechos.forEach(Hecho::resetearConsensos);
    algoritmosConsenso.forEach(algoritmo -> algoritmo.consensuar(hechos, fuentes));
  }

  public void inicializarParaConsensuado(Hecho hecho) {
    String fechaAcontecimiento = hecho.getFechaAcontecimiento().toLocalDate().toString();
    Double latitud = hecho.getUbicacion().getLatitud();
    Double longitud = hecho.getUbicacion().getLongitud();
    String geohash = GeoHash.geoHashStringWithCharacterPrecision(latitud, longitud, 7);
    String categoria = hecho.getCategoria().getNombre();
    String claveDeComparacion = categoria + "-" + fechaAcontecimiento + "-" + geohash;
    hecho.setClaveComparacion(claveDeComparacion);
    hecho.agregarConsenso(TipoConsenso.NINGUNO);
  }
}