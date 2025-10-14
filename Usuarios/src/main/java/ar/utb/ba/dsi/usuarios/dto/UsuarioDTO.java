package ar.utb.ba.dsi.usuarios.dto;

import ar.utb.ba.dsi.usuarios.models.entities.Rol;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
  @JsonProperty("username")
  private String nombreUsuario;
  @JsonProperty("password")
  private String contrasenia;
  @JsonProperty("rol")
  private Rol rol;
  @JsonProperty("fecha_nacimiento")
  private LocalDate fechaNacimiento;
  @JsonProperty("nombre")
  private String nombre;
  @JsonProperty("apellido")
  private String apellido;
}
