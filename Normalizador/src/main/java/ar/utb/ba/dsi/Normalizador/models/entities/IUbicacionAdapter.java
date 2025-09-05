package ar.utb.ba.dsi.Normalizador.models.entities;

import ar.utb.ba.dsi.Normalizador.models.DTOs.UbicacionResponse;
import reactor.core.publisher.Mono;

public interface IUbicacionAdapter {
    public Mono<UbicacionResponse> obtenerUbicacionDeAPI(Double latitud, Double longitud);
}
