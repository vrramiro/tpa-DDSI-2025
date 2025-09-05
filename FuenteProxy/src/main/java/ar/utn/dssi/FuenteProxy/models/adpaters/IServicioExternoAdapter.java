package ar.utn.dssi.FuenteProxy.models.adpaters;

import ar.utn.dssi.FuenteProxy.models.DTOs.output.HechoOutputDTO;
import ar.utn.dssi.FuenteProxy.models.entities.Hecho;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IServicioExternoAdapter {
  public Mono<List<Hecho>> obtenerHechos();
}
