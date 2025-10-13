package ar.utb.ba.dsi.usuarios.dto.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshResponse {
  private String accessToken;
  private String refreshToken;
}
