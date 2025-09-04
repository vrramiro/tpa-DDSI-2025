package ar.utn.dssi.FuenteDinamica.services;

import ar.utn.dssi.FuenteDinamica.models.DTOs.outputs.UbicacionOutputDTO;
import ar.utn.dssi.FuenteDinamica.models.entities.Ubicacion;
import reactor.core.publisher.Mono;

public interface IUbicacionService {
    public Mono<Ubicacion> obtenerUbicacionDeAPI(Double latitud, Double longitud);
}
