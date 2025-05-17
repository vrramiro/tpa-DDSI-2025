package ar.utn.dssi.FuenteDinamica.models.DTOs.inputs;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HechoInputDTO {
  private String titulo;
  private String descripcion;
  private Long idCategoria;
  private double latitud;
  private double longitud;
  private LocalDateTime fechaAcontecimiento;
}
