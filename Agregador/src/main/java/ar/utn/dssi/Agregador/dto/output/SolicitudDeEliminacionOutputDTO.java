package ar.utn.dssi.Agregador.dto.output;

import ar.utn.dssi.Agregador.models.entities.solicitud.EstadoDeSolicitud;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SolicitudDeEliminacionOutputDTO {
  @JsonProperty("id_solicitud")
  private Long idSolicitud;
  @JsonProperty("estado_solicitud")
  private EstadoDeSolicitud estadoDeSolicitud;
  @JsonProperty("es_spam")
  private boolean esSpam;
  @JsonProperty("fecha_creacion")
  private LocalDateTime fechaDeCreacion;
  @JsonProperty("fecha_evaliacion")
  private LocalDateTime fechaDeEvaluacion;
  @JsonProperty("descripcion")
  private String descripcion;
  @JsonProperty("hecho")
  private HechoOutputDTO hecho;
}
