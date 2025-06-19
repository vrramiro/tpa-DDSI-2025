package ar.utn.dssi.Agregador.models.entities.fuente.impl;

import ar.utn.dssi.Agregador.models.DTOs.external.HechosDeFuente;
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

  //TODO: ES NECESARIO OBTENER TOOODOS LOS HECHOS DE LA FUENTE? SI EN CASO DE QUE NUNCA NOS MANDO NADA, NOS LOS MANDA TODOS EN HECHOSNUEVOS
  public List<HechoInputDTO> obtenerHechos() {
    return List.of();
  }

  public List<HechoInputDTO> obtenerNuevosHechos() {
    return this.fuente
            .get()
            .uri("/hechosNuevos")
            .retrieve()
            .bodyToMono(HechosDeFuente.class)
            .map(HechosDeFuente::getHechos)
            .block();
  }

}