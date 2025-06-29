package ar.utn.dssi.Agregador.models.entities.fuente;

import ar.utn.dssi.Agregador.models.entities.Origen;
import ar.utn.dssi.Agregador.models.entities.fuente.impl.TipoFuenteDinamica;
import ar.utn.dssi.Agregador.models.entities.fuente.impl.TipoFuenteEstatica;
import ar.utn.dssi.Agregador.models.entities.fuente.impl.TipoFuenteProxy;

public class TipoFuenteFactory {
  public static ITipoFuente crearTipoFuente(String url, Origen tipoFuente) {
    switch (tipoFuente) {
      case FUENTE_PROXY:
        return new TipoFuenteProxy(url, tipoFuente);
      case FUENTE_DINAMICA:
        return new TipoFuenteDinamica(url, tipoFuente);
      case FUENTE_ESTATICA:
        return new TipoFuenteEstatica(url, tipoFuente);
      default:
        throw new IllegalArgumentException("Origen desconocido: " + tipoFuente); //TODO REVISAR
    }
  }
}
