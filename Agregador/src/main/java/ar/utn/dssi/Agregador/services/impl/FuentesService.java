package ar.utn.dssi.Agregador.services.impl;

import ar.utn.dssi.Agregador.models.DTOs.external.HechosMetaMapa;
import ar.utn.dssi.Agregador.models.DTOs.inputDTO.HechoInputDTO;
import ar.utn.dssi.Agregador.services.IFuentesService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.ArrayList;
import java.util.List;

@Service
public class FuentesService implements IFuentesService {
  private WebClient fuenteEstatica;
  private WebClient fuenteDinamica;
  private WebClient fuenteProxy;

  public FuentesService() {
    //TODO implementar controllers de fuentes
  }

  @Override
  public List<HechoInputDTO> obtenerNuevosHechos() {
    List<HechoInputDTO> hechosNuevos = new ArrayList<>();

    hechosNuevos.addAll(this.pedirHechosNuevosA(fuenteEstatica));

    hechosNuevos.addAll(this.pedirHechosNuevosA(fuenteDinamica));

    hechosNuevos.addAll(pedirHechosNuevosA(fuenteProxy));

    return hechosNuevos;
  }

  @Override
  //Los hechos output de una fuente metamapa son iguales a los hechos output
  public List<HechoInputDTO> obtenerHechosMetamapa() {
    return fuenteProxy
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

//TODO tener en cuenta que puede haber varias instancias de fuentes -> como gestionar

