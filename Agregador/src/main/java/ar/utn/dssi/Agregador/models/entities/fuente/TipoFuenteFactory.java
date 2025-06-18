package ar.utn.dssi.Agregador.models.entities.fuente;

import ar.utn.dssi.Agregador.models.entities.fuente.impl.TipoFuenteDinamica;
import ar.utn.dssi.Agregador.models.entities.fuente.impl.TipoFuenteEstatica;
import ar.utn.dssi.Agregador.models.entities.fuente.impl.TipoFuenteProxy;

public class TipoFuenteFactory {
  public static ITipoFuente crearTipoFuente(String url, String tipoFuente) {
    switch (tipoFuente) {
      case "PROXY":
        return new TipoFuenteProxy(url);
      case "DINAMICA":
        return new TipoFuenteDinamica(url);
      case "ESTATICA":
        return new TipoFuenteEstatica(url);
      default:
        throw new IllegalArgumentException("Origen desconocido: " + tipoFuente);
    }
  }
}
