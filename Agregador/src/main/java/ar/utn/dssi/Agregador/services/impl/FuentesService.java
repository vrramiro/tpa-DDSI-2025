package ar.utn.dssi.Agregador.services.impl;

import ar.utn.dssi.Agregador.models.DTOs.external.HechosMetaMapa;
import ar.utn.dssi.Agregador.models.DTOs.inputDTO.HechoInputDTO;
import ar.utn.dssi.Agregador.models.entities.fuente.Fuente;
import ar.utn.dssi.Agregador.services.IFuentesService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.ArrayList;
import java.util.List;

@Service
public class FuentesService implements IFuentesService {
  private List<Fuente> fuentes;

  public FuentesService() {
    //TODO implementar controllers de fuentes
  }

  @Override
  public List<HechoInputDTO> obtenerNuevosHechos() {
    //TODO
    return List.of();
  }

  @Override
  public List<HechoInputDTO> obtenerHechosMetamapa() {
    //TODO ya no es solo metamapa sino hechos proxy en general (se traen en tiempo real)

    return fuentesProxy
        .get()
        .uri(uriBuilder ->
            uriBuilder
                .path("/fuente/hechos/metamapa") //este endpoint al igual que el resto aun no existe
                .build())
        .retrieve()
        .bodyToMono(HechosMetaMapa.class)
        .map(HechosMetaMapa::getHechos)
        .block();
  }

  private List<HechoInputDTO> pedirHechosNuevosA(WebClient fuente){
    //TODO REFACTOR Ademas revisar parametros, ahora hay una lista de fuentes, etc.

    return fuente
        .get()
        .uri(uriBuilder ->
            uriBuilder
                //TODO implementar
                .path("/fuente/hechos")
               //.queryParam("enviado", false)
                .build()
        )
        .retrieve()
        .bodyToFlux(HechoInputDTO.class)
        .collectList()
        .block();
  }
}
