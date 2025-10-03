package ar.utn.dssi.Agregador.models.entities.fuente.impl;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.fuentes.HechoFuenteProxyInputDTO;
import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.fuente.Fuente;
import ar.utn.dssi.Agregador.models.entities.fuente.ITipoProxy;
import ar.utn.dssi.Agregador.models.mappers.MapperDeHechos;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class FuenteProxy implements ITipoProxy {


  @Override
  public List<Hecho> hechosNuevos(Fuente fuente) {
    return this.getHechos(fuente.getBaseUrl()).stream().map(MapperDeHechos::hechoFromInputDTOProxy).toList();
  }

  @Override
  public List<Hecho> hechosMetamapa(Fuente fuente) {
    return this.getHechosMetamapa(fuente.getBaseUrl()).stream().map(MapperDeHechos::hechoFromInputDTOProxy).toList();
  }

  public List<HechoFuenteProxyInputDTO> getHechos(String baseUrl) {
    WebClient webClient = WebClient.builder().baseUrl(baseUrl + "/api/hechos/metamapa?=false").build();

    return webClient.get()
            .retrieve()
            .bodyToFlux(HechoFuenteProxyInputDTO.class)
            .collectList()
            .block();
  }

  public List<HechoFuenteProxyInputDTO> getHechosMetamapa(String baseUrl) {
    WebClient webClient = WebClient.builder().baseUrl(baseUrl + "/api/hechos/metamapa?=true").build();

    return webClient.get()
            .retrieve()
            .bodyToFlux(HechoFuenteProxyInputDTO.class)
            .collectList()
            .block();
  }
}
