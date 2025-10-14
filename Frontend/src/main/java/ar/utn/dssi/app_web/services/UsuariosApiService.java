package ar.utn.dssi.app_web.services;

import ar.utn.dssi.app_web.dto.Users.AuthResponseDTO;
import ar.utn.dssi.app_web.dto.Users.RolDTO;
import ar.utn.dssi.app_web.dto.Users.UserRequest;
import ar.utn.dssi.app_web.services.internal.WebApiCallerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Map;


@Service
public class UsuariosApiService {
    private final WebClient webClient;
    private final WebApiCallerService webApiCallerService;
    private final String usuariosServiceUrl;
    private final String authServiceUrl;

    public UsuariosApiService(
            WebApiCallerService webApiCallerService,
            @Value("${auth.service.url}") String authServiceUrl,
            @Value("${usuarios.service.url}") String usuariosServiceUrl) {
        this.webClient = WebClient.builder().build();
        this.webApiCallerService = webApiCallerService;
        this.authServiceUrl = authServiceUrl;
        this.usuariosServiceUrl = usuariosServiceUrl;
    }

    public AuthResponseDTO login(String username, String password) {
        try {
            AuthResponseDTO response = webClient
                    .post()
                    .uri(authServiceUrl + "/auth")
                    .bodyValue(Map.of(
                            "username", username,
                            "password", password
                    ))
                    .retrieve()
                    .bodyToMono(AuthResponseDTO.class)
                    .block();
            return response;
        } catch (WebClientResponseException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return null;
            }
            throw new RuntimeException("Error en el servicio de autenticación: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Error de conexión con el servicio de autenticación: " + e.getMessage(), e);
        }    }


    // En UsuariosApiService.java

    public Boolean registroUsuario(UserRequest userRequest) {
        userRequest.setRol("CONTRIBUYENTE");
        try {
            webClient
                    .post()
                    .uri(usuariosServiceUrl + "/usuarios")
                    .bodyValue(userRequest)
                    .retrieve()
                    .toBodilessEntity()
                    .block();

            return true;

        } catch (WebClientResponseException e) {

            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new IllegalArgumentException("Faltan datos obligatorios o hay errores en el formulario.");
            }
            else if (e.getStatusCode() == HttpStatus.CONFLICT) {
                throw new IllegalStateException("El nombre de usuario no está disponible. Intenta con otro.");
            }
            else {
                throw new RuntimeException("Error inesperado en el servicio de usuarios: " + e.getStatusCode());
            }

        } catch (Exception e) {
            System.err.println("!!! [UsuariosApiService] Error de conexión: " + e.getMessage());
            throw new RuntimeException("No se pudo conectar con el servicio de usuarios.");
        }
    }



}
