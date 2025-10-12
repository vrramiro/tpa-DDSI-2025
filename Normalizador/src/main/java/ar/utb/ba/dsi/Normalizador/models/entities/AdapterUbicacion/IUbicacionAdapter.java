package ar.utb.ba.dsi.Normalizador.models.entities.AdapterUbicacion;

import ar.utb.ba.dsi.Normalizador.models.entities.Ubicacion;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public interface IUbicacionAdapter {
  public Mono<Ubicacion> obtenerUbicacionDeAPI(Double latitud, Double longitud);
}
