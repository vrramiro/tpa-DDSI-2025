package ar.utn.dssi.Agregador.models.entities.fuente.impl;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.fuentes.HechoFuenteEstaticaIntputDTO;
import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.fuente.Fuente;
import ar.utn.dssi.Agregador.models.entities.fuente.ITipoFuente;
import ar.utn.dssi.Agregador.models.mappers.MapperDeHechos;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class FuenteEstatica implements ITipoFuente {


  @Override
  public List<Hecho> hechosNuevos(Fuente fuente) {
    return this.getHechos(fuente.getBaseUrl()).stream().map(MapperDeHechos::hechoFromInputDTOEstatica).toList();
  }

  public List<HechoFuenteEstaticaIntputDTO> getHechos(String baseUrl) {
    WebClient webClient = WebClient.builder().baseUrl(baseUrl + "/api/hechos").build();

    return webClient.get()
            .retrieve()
            .bodyToFlux(HechoFuenteEstaticaIntputDTO.class)
            .collectList()
            .block()
            .stream()
            .toList();
  }
}
