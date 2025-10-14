package ar.utb.ba.dsi.Normalizador.models.entities.AdapterUbicacion;

import ar.utb.ba.dsi.Normalizador.models.entities.Ubicacion;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import java.util.List;

@Component
public interface IUbicacionAdapter {
  Mono<Ubicacion> obtenerUbicacionDeAPI(Double latitud, Double longitud);

  Mono<List<String>> obtenerProvinciasDeAPI();
}
