package ar.utn.dssi.Agregador.models.entities.fuente.impl;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.fuentes.HechoFuenteDinamicaInputDTO;
import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.fuente.Fuente;
import ar.utn.dssi.Agregador.models.entities.fuente.ITipoFuente;
import ar.utn.dssi.Agregador.models.mappers.MapperDeHechos;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class FuenteDinamica implements ITipoFuente {

  @Override
  public List<Hecho> hechosNuevos(Fuente fuente) {
    return this.getHechos(fuente.getBaseUrl()).stream().map(MapperDeHechos::hechoFromInputDTODinamica).toList();
  }

  public List<HechoFuenteDinamicaInputDTO> getHechos(String baseUrl) {
    WebClient webClient = WebClient.builder().baseUrl(baseUrl + "/api/hechos").build();

    return webClient.get()
            .retrieve()
            .bodyToFlux(HechoFuenteDinamicaInputDTO.class)  //BODY TO MONO
            .collectList()
            .block();
  }
  //abstraer fdconcreta y hacer el mapper aca!!
}
