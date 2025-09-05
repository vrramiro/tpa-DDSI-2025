package ar.utb.ba.dsi.Normalizador.models.entities;

import ar.utb.ba.dsi.Normalizador.models.DTOs.Output.UbicacionOutputDTO;
import reactor.core.publisher.Mono;

public interface IUbicacionAdapter {
    public Mono<UbicacionOutputDTO> obtenerUbicacionDeAPI(Double latitud, Double longitud);
}
