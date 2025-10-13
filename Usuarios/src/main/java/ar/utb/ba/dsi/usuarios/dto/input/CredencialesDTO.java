package ar.utb.ba.dsi.usuarios.dto.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CredencialesDTO {
  @JsonProperty("username")
  private String nombreUsuario;
  @JsonProperty("password")
  private String contrasenia;
}
