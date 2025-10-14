package ar.utn.dssi.app_web.providers;

import ar.utn.dssi.app_web.dto.Users.AuthResponseDTO;
import ar.utn.dssi.app_web.services.UsuariosApiService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.*;

@Component  //implementa la interfaz de spring, esta va a tener inyectada la api service
@RequiredArgsConstructor
public class CustomAuthProvider implements AuthenticationProvider {

  private final UsuariosApiService usuariosApiService;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    // --- LÍNEAS DE DEPURACIÓN ---
    System.out.println("Intentando autenticar usuario: " + authentication.getName());
    System.out.println("Credenciales recibidas: " + authentication.getCredentials());
    // ----------------------------

      String username = authentication.getName();
      String password = authentication.getCredentials().toString();

      try {
        AuthResponseDTO authResponse = usuariosApiService.login(username, password);

        if (authResponse == null || authResponse.getAccessToken() == null) {
          throw new BadCredentialsException("Usuario o contraseña inválidos");
        }

        String accessToken = authResponse.getAccessToken();
        String rolUsuario = obtenerRolDesdeToken(accessToken);

        if (rolUsuario == null) {
          throw new BadCredentialsException("No se pudo obtener el rol desde el token.");
        }

        //Aca guardo informacion en la sesion HTTP
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        request.getSession().setAttribute("accessToken", authResponse.getAccessToken());
        request.getSession().setAttribute("refreshToken", authResponse.getRefreshToken());
        request.getSession().setAttribute("username", username);


        System.out.println("Credenciales recibidas desde auth: " + rolUsuario);

        request.getSession().setAttribute("rol", rolUsuario);

        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + rolUsuario)
        );
        return new UsernamePasswordAuthenticationToken(username, null, authorities);
      } catch (RuntimeException e) {
        throw new BadCredentialsException("Error en el sistema de autenticación: " + e.getMessage());
      }
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }

  private String obtenerRolDesdeToken(String token) {
    try {
      String[] chunks = token.split("\\.");
      Base64.Decoder decoder = Base64.getUrlDecoder();
      String payload = new String(decoder.decode(chunks[1]));
      Map<String, Object> claims = new ObjectMapper().readValue(payload, Map.class);
      return (String) claims.get("rol");

    } catch (Exception e) {
      System.err.println("Error al decodificar el token JWT: " + e.getMessage());
      return null;
    }
  }

}
