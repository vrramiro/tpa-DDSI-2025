package ar.utn.dssi.Agregador.models.entities.fuente.impl.fuenteEstatica;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.HechoInputDTO;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;

@Component
@NoArgsConstructor
public class FuenteEstaticaConcreta {
  public List<HechoInputDTO> getHechos(String baseUrl) {
    WebClient webClient = WebClient.builder().baseUrl(baseUrl + "/api/hechos").build();

    return webClient.get()
        .retrieve()
        .bodyToFlux(HechoInputDTO.class)
        .collectList()
        .block()
        .stream()
        .toList();
  }
}
