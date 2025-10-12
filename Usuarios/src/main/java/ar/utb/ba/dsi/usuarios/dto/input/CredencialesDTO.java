package ar.utb.ba.dsi.usuarios.dto.input;

import lombok.Data;

@Data
public class CredencialesDTO {
  private String nombreUsuario;
  private String contrasenia;
}
