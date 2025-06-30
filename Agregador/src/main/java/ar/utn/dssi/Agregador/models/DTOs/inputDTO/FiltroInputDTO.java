package ar.utn.dssi.Agregador.models.DTOs.inputDTO;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class FiltroInputDTO {
  private String categoria;
  private LocalDate fecha_reporte_desde;
  private LocalDate fecha_reporte_hasta;
  private LocalDate fecha_acontecimiento_desde;
  private LocalDate fecha_acontecimiento_hasta;
  private Double longitud;
  private Double latitud;
  private Long idFuente;
}
