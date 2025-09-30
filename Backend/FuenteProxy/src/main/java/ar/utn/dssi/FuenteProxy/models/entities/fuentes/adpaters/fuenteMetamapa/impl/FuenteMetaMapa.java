package ar.utn.dssi.FuenteProxy.models.entities.fuentes.adpaters.fuenteMetamapa.impl;

import ar.utn.dssi.FuenteProxy.models.DTOs.output.HechoOutputDTO;
import ar.utn.dssi.FuenteProxy.models.entities.Hecho;
import ar.utn.dssi.FuenteProxy.models.entities.fuentes.TipoFuente;
import ar.utn.dssi.FuenteProxy.models.entities.fuentes.adpaters.Apis.MetamapaApi;
import ar.utn.dssi.FuenteProxy.models.entities.fuentes.adpaters.fuenteMetamapa.IFuenteMetaMapa;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

public class FuenteMetaMapa implements IFuenteMetaMapa {
    private final MetamapaApi metamapaApi;

    public FuenteMetaMapa(MetamapaApi metamapaApi) {
        this.metamapaApi = metamapaApi;
    }

    @Override
    public Mono<List<Hecho>> obtenerHechos() {
        return metamapaApi.obtenerHechos()
                .map(hechosMetaMapa -> hechosMetaMapa.getHechosInstanciaMetaMapa()
                        .stream()
                        .map(this::mapToHecho)
                        .collect(Collectors.toList())
                );
    }


    private Hecho mapToHecho(HechoOutputDTO dto) {
        Hecho hecho = new Hecho();
        hecho.setTitulo(dto.getTitulo());
        hecho.setDescripcion(dto.getDescripcion());
        hecho.setCategoria(dto.getCategoria());
        hecho.setUbicacion(dto.getUbicacion());
        hecho.setFechaAcontecimiento(dto.getFechaAcontecimiento());
        hecho.setFechaCarga(dto.getFechaCarga());
        return hecho;
    }
}