package ar.utn.dssi.FuenteEstatica.models.entities.normalizadorAdapter;


import ar.utn.dssi.FuenteEstatica.models.entities.contenido.Hecho;
import reactor.core.publisher.Mono;

public interface INormalizadorAdapter {
  Mono<Hecho> obtenerHechoNormalizado(Hecho hecho);
}
