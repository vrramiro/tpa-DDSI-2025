package ar.utb.ba.dsi.usuarios.dto.output;

import ar.utb.ba.dsi.usuarios.models.entities.Rol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class UserRolesDTO {
  private String username;
  private Rol rol;
}
