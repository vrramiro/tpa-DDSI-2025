package ar.utn.dssi.FuenteProxy.models.entities.fuentes.adpaters;

import ar.utn.dssi.FuenteProxy.models.entities.Hecho;
import reactor.core.publisher.Mono;
import java.util.List;

public interface IServicioExternoAdapter {
  Mono<List<Hecho>> obtenerHechos();
}
