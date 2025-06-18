package ar.utn.dssi.Agregador.models.entities.fuente.impl;

import ar.utn.dssi.Agregador.models.DTOs.external.HechosDeFuente;
import ar.utn.dssi.Agregador.models.DTOs.inputDTO.HechoInputDTO;
import ar.utn.dssi.Agregador.models.entities.fuente.ITipoFuente;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;

public class TipoFuenteProxy implements ITipoFuente {
  private WebClient fuente;

  public TipoFuenteProxy(String url) {
    this.fuente = WebClient.builder().baseUrl(url).build();
  }

  public List<HechoInputDTO> obtenerHechos() {
    //TODO revisar, no estoy seguro de que el pedido sea correcto (no digo que este mal, no lo se)

    return fuente
        .get()
        .retrieve()
        .bodyToMono(HechosDeFuente.class)
        .map(HechosDeFuente::getHechos)
        .block();
  }
}
