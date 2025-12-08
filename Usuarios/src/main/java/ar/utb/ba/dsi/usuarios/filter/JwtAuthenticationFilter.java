package ar.utb.ba.dsi.usuarios.filter;

import ar.utb.ba.dsi.usuarios.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Collections;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {

    try {
      String token = extraerToken(request);
      if (token != null) {
        String username = JwtUtil.validarToken(token);
        String rol = JwtUtil.extraerClaim(token, "rol");
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
            username,
            null,
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + rol))
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
      }
    } catch (Exception e) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido");
      return;
    }

    filterChain.doFilter(request, response);
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    String path = request.getRequestURI();
    // No aplicar el filtro JWT solo a los endpoints públicos de autenticación
    return path.equals("/auth") || path.equals("/auth/refresh") || path.equals("/usuarios");
  }

  private String extraerToken(HttpServletRequest request) {
    String header = request.getHeader("Authorization");
    if (header != null && header.startsWith("Bearer ")) {
      return header.substring(7);
    }
    return null;
  }
}
