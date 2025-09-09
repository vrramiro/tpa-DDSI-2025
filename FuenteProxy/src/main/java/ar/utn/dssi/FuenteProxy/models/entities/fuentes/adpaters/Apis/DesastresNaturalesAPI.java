package ar.utn.dssi.FuenteProxy.models.entities.fuentes.adpaters.Apis;

import ar.utn.dssi.FuenteProxy.models.DTOs.external.DesastresNaturales.DatosLogin;
import ar.utn.dssi.FuenteProxy.models.DTOs.external.DesastresNaturales.HechosDesastresNaturales;
import ar.utn.dssi.FuenteProxy.models.DTOs.external.DesastresNaturales.RespuestaLogin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

// -------------------- ADAPTEE --------------------
@Component
public class DesastresNaturalesAPI {

    private final WebClient webClient;

    public DesastresNaturalesAPI() {
        this.webClient = WebClient.builder()
                .baseUrl("https://api-ddsi.disilab.ar/public")
                .build();
    }

    public Mono<String> autenticar(String email, String password) {
        DatosLogin datosLogin = new DatosLogin(email, password);
        return webClient.post()
                .uri("/auth/login")
                .bodyValue(datosLogin)
                .retrieve()
                .bodyToMono(RespuestaLogin.class)
                .map(respuesta -> respuesta.getData().getAccess_token());
    }

    public Mono<HechosDesastresNaturales> obtenerHechosPorPagina(String token, int pagina, int perPage) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/desastres")
                        .queryParam("page", pagina)
                        .queryParam("per_page", perPage)
                        .build())
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(HechosDesastresNaturales.class);
    }
}