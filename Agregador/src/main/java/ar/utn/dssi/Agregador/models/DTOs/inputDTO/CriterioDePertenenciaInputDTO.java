package ar.utn.dssi.Agregador.models.DTOs.inputDTO;

import lombok.Data;

@Data
public class CriterioDePertenenciaInputDTO {
  private Long id; // En caso de quesea un criterio ya cargado, me permite actualizarlo en caso de ser necesario
  private String tipo; // El tipo de criterio, por ejemplo, PROVINCIA, PAIS, FECHA_DESDE, FECHA_HASTA
  private String valor; // El valor asociado al criterio, por ejemplo, "Buenos Aires" para provincia o "Argentina" para país o una fecha "2023-01-01" para fecha
}
