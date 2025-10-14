package ar.utn.dssi.app_web.dto.Users;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserRequest {
    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;
    @JsonProperty("rol")
    private String rol;
    @JsonProperty("fecha_nacimiento")
    private LocalDate fechaNacimiento;
    @JsonProperty("nombre")
    private String nombre;
    @JsonProperty("apellido")
    private String apellido;
}
