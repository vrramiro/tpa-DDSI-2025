package ar.utn.dssi.FuenteProxy.dto.input;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class HechoInputDTO {
  private String titulo;
  private String descripcion;
  private String categoria;
  private Double latitud;
  private Double longitud;
  private LocalDateTime fechaAcontecimiento;
}