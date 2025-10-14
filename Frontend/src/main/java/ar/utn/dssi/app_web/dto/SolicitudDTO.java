package ar.utn.dssi.app_web.dto;

import ar.utn.dssi.app_web.dto.output.HechoOutputDTO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SolicitudDTO {
    private Long idSolicitud;
    private String descripcion;
    private HechoOutputDTO hecho;
    private String autor;
    private String estadoDeSolicitud; //porque si cambia el enum del back me puede romper
    private LocalDateTime fechaDeCreacion;
    private LocalDateTime fechaDeEvaluacion;
    private String gestionadoPor;
    private boolean esSpam;
}
