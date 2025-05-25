package ar.utn.dssi.FuenteProxy.models.adapter.adaptados;

import ar.utn.dssi.FuenteProxy.models.DTOs.output.HechoOutputDTO;
import lombok.Value;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;

public class DesastresNaturales {
  private final WebClient webClient;

  //TODO: completar instanciacion de webClient
  public DesastresNaturales() {
    this.webClient = WebClient.builder().baseUrl("https://api-ddsi.disilab.ar/public").build();
  }

  //TODO
  private List<HechoOutputDTO> obtenerHechosDeAPI() {
    return List.of();
  }
}
