package ar.utb.ba.dsi.usuarios.utils;

import ar.utb.ba.dsi.usuarios.models.entities.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import java.security.Key;
import java.util.Date;

public class JwtUtil {
  @Getter
  private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

  private static final long ACCESS_TOKEN_VALIDITY = 15 * 60 * 1000; // 15 min
  private static final long REFRESH_TOKEN_VALIDITY = 7 * 24 * 60 * 60 * 1000; // 7 días

  public static String generarAccessToken(Usuario usuario) {

    return Jwts.builder()
        .setSubject(usuario.getNombre())
        .claim("rol", usuario.getRol())
        .setIssuer("gestion-alumnos-server")
        .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY))
        .signWith(key)
        .compact();
  }

  public static String generarRefreshToken(Usuario usuario) {

    return Jwts.builder()
        .setSubject(usuario.getNombre())
        .setIssuer("gestion-alumnos-server")
        .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY))
        .claim("type", "refresh") // diferenciamos refresh del access
        .signWith(key)
        .compact();
  }

  public static String validarToken(String token) {

    return Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }
}

