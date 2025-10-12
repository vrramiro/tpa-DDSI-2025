package ar.utn.dssi.FuenteDinamica.models.entities.normalizadorAdapter;

import ar.utn.dssi.FuenteDinamica.models.DTOs.outputs.UbicacionOutputDTONormalizador;
import ar.utn.dssi.FuenteDinamica.models.entities.Hecho;
import ar.utn.dssi.FuenteDinamica.models.entities.Ubicacion;
import reactor.core.publisher.Mono;

public interface INormalizadorAdapter {
  public Mono<Hecho> obtenerHechoNormalizado(Hecho hecho);

  public Mono<Ubicacion> obtenerUbicacionNormalizada(UbicacionOutputDTONormalizador ubicacionDTO);
}
