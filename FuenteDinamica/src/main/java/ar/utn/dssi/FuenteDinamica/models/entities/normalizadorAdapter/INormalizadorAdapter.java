package ar.utn.dssi.FuenteDinamica.models.entities.normalizadorAdapter;

import ar.utn.dssi.FuenteDinamica.models.entities.Categoria;
import ar.utn.dssi.FuenteDinamica.models.entities.Hecho;
import ar.utn.dssi.FuenteDinamica.models.entities.Ubicacion;
import reactor.core.publisher.Mono;

public interface INormalizadorAdapter {
  Mono<Hecho> obtenerHechoNormalizado(Hecho hecho);

  Mono<Categoria> obtenerCategoria(Long idCategoria);

  Mono<Ubicacion> obtenerUbicacionNormalizada(Double latitud, Double longitud);
}
