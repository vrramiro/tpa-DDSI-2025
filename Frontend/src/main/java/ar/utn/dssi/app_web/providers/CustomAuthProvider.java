package ar.utn.dssi.app_web.providers;

import ar.utn.dssi.app_web.dto.AuthResponseDTO;
import ar.utn.dssi.app_web.dto.RolDTO;
import ar.utn.dssi.app_web.services.UsuariosApiService;
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
      String username = authentication.getName();
      String password = authentication.getCredentials().toString();

      try {
        AuthResponseDTO authResponse = usuariosApiService.login(username, password);

        if (authResponse == null || authResponse.getAccessToken() == null) {
          throw new BadCredentialsException("Usuario o contraseña inválidos");
        }

        //Aca guardo informacion en la sesion HTTP
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        request.getSession().setAttribute("accessToken", authResponse.getAccessToken());
        request.getSession().setAttribute("refreshToken", authResponse.getRefreshToken());
        request.getSession().setAttribute("username", username);

        RolDTO rolUsuario = usuariosApiService.obtenerRoles(authResponse.getAccessToken());

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
}
