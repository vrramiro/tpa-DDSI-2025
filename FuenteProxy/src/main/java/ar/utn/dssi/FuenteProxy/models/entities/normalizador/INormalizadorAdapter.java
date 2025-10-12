package ar.utn.dssi.FuenteProxy.models.entities.normalizador;


import ar.utn.dssi.FuenteProxy.models.entities.Hecho;
import reactor.core.publisher.Mono;

public interface INormalizadorAdapter {
    public Mono<Hecho> obtenerHechoNormalizado(Hecho hecho);
}
