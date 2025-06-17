package ar.utn.dssi.Agregador.services;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.SolicitudDeEliminacionInputDTO;
import ar.utn.dssi.Agregador.models.entities.content.Hecho;
import ar.utn.dssi.Agregador.models.entities.solicitud.SolicitudDeEliminacion;
import org.springframework.stereotype.Service;

@Service
public interface ISolicitudDeEliminacionService {
    void crearSolicitudDeEliminacion(SolicitudDeEliminacionInputDTO solicitudDeEliminacion);
    void aceptarSolicitud(Long idSolicitud);
    void rechazarSolicitud(Long idSolicitud);
}
