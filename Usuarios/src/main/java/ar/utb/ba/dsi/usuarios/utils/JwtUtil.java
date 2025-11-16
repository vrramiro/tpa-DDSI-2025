package ar.utb.ba.dsi.usuarios.utils;

import ar.utb.ba.dsi.usuarios.models.entities.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

  private final Key key;
  private static final long ACCESS_TOKEN_VALIDITY = 15 * 60 * 1000; // 15 min
  private static final long REFRESH_TOKEN_VALIDITY = 7 * 24 * 60 * 60 * 1000; // 7 días

  public JwtUtil(@Value("${jwt.secret}") String secretString) {
    byte[] keyBytes = Decoders.BASE64.decode(secretString);
    this.key = Keys.hmacShaKeyFor(keyBytes);
  }

  public String generarAccessToken(Usuario usuario) {
    return Jwts.builder()
            .setSubject(usuario.getNombreUsuario())
            .claim("rol", usuario.getRol().name())
            .setIssuer("gestion-usuarios-server")
            .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY))
            .signWith(this.key)
            .compact();
  }

  public String generarRefreshToken(Usuario usuario) {
    return Jwts.builder()
            .setSubject(usuario.getNombreUsuario())
            .setIssuer("gestion-usuarios-server")
            .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY))
            .claim("type", "refresh")
            .signWith(this.key)
            .compact();
  }

  public String validarToken(String token) {
    return Jwts.parserBuilder()
            .setSigningKey(this.key)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
  }

  public String extraerClaim(String token, String claim) {
    return Jwts.parserBuilder()
            .setSigningKey(this.key)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .get(claim, String.class);
  }

  public Claims extraerClaims(String token) {
    return Jwts.parserBuilder()
            .setSigningKey(this.key)
            .build()
            .parseClaimsJws(token)
            .getBody();
  }
}