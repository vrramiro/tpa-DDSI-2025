package ar.utn.dssi.FuenteProxy.models.fuenteMetamapa.impl;

import ar.utn.dssi.FuenteProxy.models.DTOs.external.MetaMapa.HechosMetaMapa;
import ar.utn.dssi.FuenteProxy.models.DTOs.output.HechoOutputDTO;
import ar.utn.dssi.FuenteProxy.models.fuenteMetamapa.IFuenteMetaMapa;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;

@Component
public class FuenteMetaMapa implements IFuenteMetaMapa {
  private WebClient instanciaMetamapa;

  public FuenteMetaMapa(WebClient.Builder webClientBuilder) {
    //fuente mokeada con postman
    this.instanciaMetamapa = webClientBuilder.baseUrl("https://88019b83-d71c-4909-a36a-fbeb7145813c.mock.pstmn.io").build();
  }

  @Override
  public List<HechoOutputDTO> obtenerHechosMetamapa() {
    return instanciaMetamapa
        .get()
        .uri(uriBuilder -> uriBuilder
            .path("/api/hechos")
            .build())
        .retrieve()
        .bodyToMono(HechosMetaMapa.class)
        .map(HechosMetaMapa::getHechosInstanciaMetaMapa)
        .block();
  }
}
