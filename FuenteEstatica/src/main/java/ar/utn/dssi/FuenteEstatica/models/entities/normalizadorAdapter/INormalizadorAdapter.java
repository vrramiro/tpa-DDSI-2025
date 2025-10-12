package ar.utn.dssi.FuenteEstatica.models.entities.normalizadorAdapter;


import ar.utn.dssi.FuenteEstatica.models.DTOs.output.HechoOutputDTONormalizador;
import ar.utn.dssi.FuenteEstatica.models.entities.contenido.Hecho;
import reactor.core.publisher.Mono;

public interface INormalizadorAdapter {
    public Mono<Hecho> obtenerHechoNormalizado(Hecho hecho);
}
