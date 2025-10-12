package ar.utn.dssi.FuenteProxy.models.entities.fuentes.adpaters.concretos;

import ar.utn.dssi.FuenteProxy.dto.external.DesastresNaturales.DatosLogin;
import ar.utn.dssi.FuenteProxy.dto.external.DesastresNaturales.HechosDesastresNaturales;
import ar.utn.dssi.FuenteProxy.dto.external.DesastresNaturales.RespuestaLogin;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class DesastresNaturalesConcreto {
  private final WebClient webClient;

  public DesastresNaturalesConcreto(String baseUrl) {
    this.webClient = WebClient.builder()
        .baseUrl(baseUrl)
        .build();
  }

  public Mono<String> autenticar(String email, String password) {
    DatosLogin datosLogin = new DatosLogin(email, password);
    return webClient.post()
        .uri("/login")
        .bodyValue(datosLogin)
        .retrieve()
        .bodyToMono(RespuestaLogin.class)
        .map(respuesta -> respuesta.getData().getAccess_token());
  }

  public Mono<HechosDesastresNaturales> obtenerHechosPorPagina(String token, int pagina, int perPage) {
    return webClient.get()
        .uri(uriBuilder -> uriBuilder
            .path("/desastres")
            .queryParam("page", pagina)
            .queryParam("per_page", perPage)
            .build())
        .header("Authorization", "Bearer " + token)
        .retrieve()
        .bodyToMono(HechosDesastresNaturales.class);
  }
}