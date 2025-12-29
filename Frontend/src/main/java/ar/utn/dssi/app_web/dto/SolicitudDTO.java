package ar.utn.dssi.app_web.dto;

import ar.utn.dssi.app_web.dto.output.HechoOutputDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SolicitudDTO {
    @JsonProperty("id_solicitud")
    private Long idSolicitud;

    @JsonProperty("autor")
    private String autor;

    @JsonProperty("estado_solicitud")
    private String estadoDeSolicitud;

    @JsonProperty("es_spam")
    private boolean esSpam;
    @JsonProperty("fecha_creacion")
    private LocalDateTime fechaDeCreacion;
    @JsonProperty("fecha_evaluacion")
    private LocalDateTime fechaDeEvaluacion;
    @JsonProperty("descripcion")
    private String descripcion;
    @JsonProperty("hecho")
    private HechoOutputDTO hecho;
}