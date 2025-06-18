package ar.utn.dssi.Agregador.models.entities.fuente.impl;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.HechoInputDTO;
import ar.utn.dssi.Agregador.models.entities.fuente.ITipoFuente;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;

public class TipoFuenteDinamica implements ITipoFuente {
  private WebClient fuente;
  public TipoFuenteDinamica(String url) {
    this.fuente = WebClient.builder().baseUrl(url).build();
  }

  public List<HechoInputDTO> obtenerHechos() {
    return List.of();
  }
}
