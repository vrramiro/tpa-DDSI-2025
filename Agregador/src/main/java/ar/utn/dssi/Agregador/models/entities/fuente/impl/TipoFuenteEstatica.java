package ar.utn.dssi.Agregador.models.entities.fuente.impl;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.HechoInputDTO;
import ar.utn.dssi.Agregador.models.entities.content.Origen;
import ar.utn.dssi.Agregador.models.entities.fuente.IFuenteRefrescable;
import ar.utn.dssi.Agregador.models.entities.fuente.ITipoFuente;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;

public class TipoFuenteEstatica implements ITipoFuente, IFuenteRefrescable {
  private WebClient fuente;
  private Origen tipo;

  public TipoFuenteEstatica(String url, Origen tipo) {
    this.tipo = tipo;
    this.fuente = WebClient.builder().baseUrl(url).build();
  }

  public Origen tipo() {
    return tipo;
  }

  //TODO
  public List<HechoInputDTO> obtenerHechos() {
    return List.of();
  }

  //TODO
  public List<HechoInputDTO> obtenerNuevosHechos() {
    return List.of();
  }

}