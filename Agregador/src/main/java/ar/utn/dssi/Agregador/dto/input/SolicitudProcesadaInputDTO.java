package ar.utn.dssi.Agregador.dto.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SolicitudProcesadaInputDTO {
  @JsonProperty("estado")
  private String estado; // "APROBADA" o "RECHAZADA"
}
