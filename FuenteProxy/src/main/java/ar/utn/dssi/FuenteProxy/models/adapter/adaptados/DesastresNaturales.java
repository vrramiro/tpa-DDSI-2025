package ar.utn.dssi.FuenteProxy.models.adapter.adaptados;

import ar.utn.dssi.FuenteProxy.models.DTOs.external.DesastresNaturales.HechoDesastresNaturales;
import ar.utn.dssi.FuenteProxy.models.DTOs.external.DesastresNaturales.HechosDesastresNaturales;
import ar.utn.dssi.FuenteProxy.models.DTOs.output.HechoOutputDTO;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.ArrayList;
import java.util.List;

public class DesastresNaturales {
  private final WebClient webClient;

  //TODO: completar instanciacion de webClient
  public DesastresNaturales() {
    this.webClient = WebClient.builder().baseUrl("https://api-ddsi.disilab.ar/public").build();
  }

  //TODO
  public List<HechoDesastresNaturales> obtenerHechosDeAPI() {
    Integer pagina = 1;
    List<HechoDesastresNaturales> hechosObtenidos = new ArrayList<HechoDesastresNaturales>();

    while(true) {
        HechosDesastresNaturales hechosPedidos = hechosPorPagina(pagina);

        //Cuando pedimos hechos a una pagina que no tiene hechos la API devuelve una lista vacia
        if(hechosPedidos.getHechosObtenidos().isEmpty()) break;

        hechosObtenidos.addAll(hechosPedidos.getHechosObtenidos());

        if(hechosPedidos.getCurrent_page() >= hechosPedidos.getLast_page()) break;

        pagina++;
    }

    return hechosObtenidos;
  }

  private HechosDesastresNaturales hechosPorPagina(Integer pagina) {
    return webClient
        .get()
        .uri(uriBuilder -> uriBuilder
            .path("/api/desastres")
            .queryParam("page", pagina)
            .queryParam("per_page", 100)
            .build())
        .retrieve()
        .bodyToMono(HechosDesastresNaturales.class)
        .block();
  }
}
