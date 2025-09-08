package ar.utn.dssi.FuenteProxy.models.entities.fuentes.adpaters.fuenteMetamapa.impl;

import ar.utn.dssi.FuenteProxy.models.DTOs.external.MetaMapa.HechosMetaMapa;
import ar.utn.dssi.FuenteProxy.models.DTOs.output.HechoOutputDTO;
import ar.utn.dssi.FuenteProxy.models.entities.Hecho;
import ar.utn.dssi.FuenteProxy.models.entities.fuentes.TipoFuente;
import ar.utn.dssi.FuenteProxy.models.entities.fuentes.adpaters.fuenteMetamapa.IFuenteMetaMapa;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FuenteMetaMapa implements IFuenteMetaMapa {
    private WebClient instanciaMetamapa;

    public FuenteMetaMapa(WebClient.Builder webClientBuilder) {
        //fuente mokeada con postman
        this.instanciaMetamapa = webClientBuilder.baseUrl("https://88019b83-d71c-4909-a36a-fbeb7145813c.mock.pstmn.io").build();
    }

    @Override
    public Mono<List<Hecho>> obtenerHechos() {
        return instanciaMetamapa
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/hechos")
                        .build())
                .retrieve()
                .bodyToMono(HechosMetaMapa.class)
                .map(hechosMetaMapa -> hechosMetaMapa.getHechosInstanciaMetaMapa()
                .stream()
                .map(this::mapToHecho) // transformamos cada DTO en Hecho
                .collect(Collectors.toList())
        );
    }

    @Override
    public TipoFuente getTipoFuente() {
        return TipoFuente.METAMAPA;
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