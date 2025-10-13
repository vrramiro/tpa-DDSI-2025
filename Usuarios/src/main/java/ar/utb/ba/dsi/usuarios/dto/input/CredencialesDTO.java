package ar.utb.ba.dsi.usuarios.dto.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CredencialesDTO {
  @JsonProperty("nombre_usuario")
  private String nombreUsuario;
  @JsonProperty("contrasenia")
  private String contrasenia;
}
