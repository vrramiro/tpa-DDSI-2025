package ar.utn.dssi.Agregador.models.entities.fuente.impl;

import ar.utn.dssi.Agregador.models.DTOs.external.HechosDeFuente;
import ar.utn.dssi.Agregador.models.DTOs.inputDTO.HechoInputDTO;
import ar.utn.dssi.Agregador.models.entities.Origen;
import ar.utn.dssi.Agregador.models.entities.fuente.ITipoFuente;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;

public class TipoFuenteDinamica implements ITipoFuente {
  private WebClient fuente;
  private Origen tipo;

  public TipoFuenteDinamica(String url, Origen tipo) {
    this.tipo = tipo;
    this.fuente = WebClient.builder().baseUrl(url).build();
  }

  public Origen getTipo() {
    return tipo;
  }

  public List<HechoInputDTO> obtenerHechos() {
    return this.fuente
        .get()
        .uri("/hechosNuevos")
        .retrieve()
        .bodyToMono(HechosDeFuente.class)
        .map(HechosDeFuente::getHechos)
        .block();
  }
}
