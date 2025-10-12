package ar.utb.ba.dsi.usuarios.dto;

import ar.utb.ba.dsi.usuarios.models.entities.Rol;
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
    private String nombreUsuario;
    private String contrasenia;
    private Rol rol;

    private LocalDate fechaNacimiento;
    private String nombre;
    private String apellido;
}
