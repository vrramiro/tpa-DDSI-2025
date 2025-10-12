package ar.utn.dssi.Agregador.models.DTOs.inputDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SolicitudProcesadaInputDTO {
  @JsonProperty("estado") private String estado; // "APROBADA" o "RECHAZADA"
}
