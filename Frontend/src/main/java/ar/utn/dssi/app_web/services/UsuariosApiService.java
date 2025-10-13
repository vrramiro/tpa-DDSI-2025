package ar.utn.dssi.app_web.services;

import ar.utn.dssi.app_web.dto.AuthResponseDTO;
import ar.utn.dssi.app_web.dto.RolDTO;
import ar.utn.dssi.app_web.services.internal.WebApiCallerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

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

    //pide al servicio que obtenga el rol que el mismo encriptó
    public RolDTO obtenerRoles(String accessToken) {
        try {
            RolDTO response = webApiCallerService.getWithAuth(
                    authServiceUrl + "/auth/user/rol",
                    accessToken,
                    RolDTO.class
            );
            return response;
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener roles y permisos: " + e.getMessage(), e);
        }
    }


}
