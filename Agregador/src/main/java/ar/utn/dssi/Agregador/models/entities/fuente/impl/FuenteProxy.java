package ar.utn.dssi.Agregador.models.entities.fuente.impl;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.fuentes.HechoFuenteProxyInputDTO;
import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.fuente.Fuente;
import ar.utn.dssi.Agregador.models.entities.fuente.ITipoProxy;
import ar.utn.dssi.Agregador.models.entities.fuente.TipoFuente;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class FuenteProxy implements ITipoProxy {
  @Value("${timeout-ms}")
  private Integer timeoutMs;

  @Override
  public List<Hecho> hechosNuevos(Fuente fuente) {
    LocalDateTime ultimoEnvioFuente = fuente.getUltimaActualizacion();
    LocalDateTime fechaDesde;

    if (ultimoEnvioFuente == null) {
      fechaDesde = LocalDateTime.now();
    } else {
      fechaDesde = ultimoEnvioFuente;
    }

    return getHechos(fuente.getBaseUrl(), fechaDesde)
            .map(this::hechoFromInputDTOProxy)
            .collectList()
            .block();
  }

  @Override
  public TipoFuente getTipoFuente() {
    return TipoFuente.PROXY;
  }

  private Flux<HechoFuenteProxyInputDTO> getHechos(String baseUrl, LocalDateTime fechaDesde) {
    WebClient webClient = WebClient.builder()
            .baseUrl(baseUrl+ "/hechos")
            .build();

    return webClient.get()
            .uri(uriBuilder -> uriBuilder
                    .path("/nuevos")
                    .queryParam("fechaDesde", fechaDesde)
                    .build())
            .retrieve()
            .bodyToFlux(HechoFuenteProxyInputDTO.class)
            .timeout(Duration.ofMillis(timeoutMs))
            .onErrorResume(e -> {
              e.printStackTrace();
              return Flux.empty();
            });
  }

  @Override
  public List<Hecho> hechosMetamapa(Fuente fuente) {
    //TODO: VER LA IMPLEMENTACION EN LA PROXY PRIMERO
    return List.of();
  }

  public Hecho hechoFromInputDTOProxy(HechoFuenteProxyInputDTO input) {
    Hecho hecho = new Hecho();
    hecho.setIdEnFuente(input.getIdExterno());
    hecho.setTitulo(input.getTitulo());
    hecho.setDescripcion(input.getDescripcion());
    hecho.setTituloSanitizado(input.getTituloSanitizado());
    hecho.setDescripcionSanitizado(input.getDescripcionSanitizada());
    hecho.setFechaAcontecimiento(input.getFechaAcontecimiento());
    hecho.setFechaCarga(input.getFechaCarga());
    hecho.setVisible(true);
    return hecho;
  }


}
