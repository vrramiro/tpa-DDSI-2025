package ar.utn.dssi.FuenteProxy.models.DTOs.external.DesastresNaturales;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class HechosDesastresNaturales {
  private Integer current_page;
  private List<HechoDesastresNaturales> hechosObtenidos;
  private Integer last_page;
}
