package ar.utb.ba.dsi.Normalizador.dto.Input;

import lombok.Data;
import java.util.List;

@Data
public class ProvinciasInputDTO {
  private List<ProvinciaObtenida> provincias;

  @Data
  public static class ProvinciaObtenida {
    private String nombre;
  }
}
