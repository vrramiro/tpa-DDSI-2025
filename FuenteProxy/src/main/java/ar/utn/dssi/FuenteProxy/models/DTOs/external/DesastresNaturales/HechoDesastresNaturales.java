package ar.utn.dssi.FuenteProxy.models.DTOs.external.DesastresNaturales;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class HechoDesastresNaturales {
  private String titulo;
  private String descripcion;
  private String categoria;
  private Double latitud;
  private Double longitud;
  private LocalDateTime fecha_hecho;
  private LocalDateTime created_at;
}
