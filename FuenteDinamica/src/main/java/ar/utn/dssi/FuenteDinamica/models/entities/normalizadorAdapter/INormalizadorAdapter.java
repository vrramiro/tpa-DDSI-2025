package ar.utn.dssi.FuenteDinamica.models.entities.normalizadorAdapter;

import ar.utn.dssi.FuenteDinamica.models.DTOs.outputs.HechoOutputDTO;
import ar.utn.dssi.FuenteDinamica.models.DTOs.outputs.HechoOutputDTONormalizador;
import ar.utn.dssi.FuenteDinamica.models.entities.Hecho;
import reactor.core.publisher.Mono;

public interface INormalizadorAdapter {
    public Mono<Hecho> obtenerHechoNormalizado(HechoOutputDTONormalizador hechoDTO);

    }
