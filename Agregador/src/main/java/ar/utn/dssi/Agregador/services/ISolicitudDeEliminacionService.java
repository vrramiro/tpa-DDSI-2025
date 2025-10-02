package ar.utn.dssi.Agregador.services;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.SolicitudDeEliminacionInputDTO;
import ar.utn.dssi.Agregador.models.DTOs.outputDTO.SolicitudDeEliminacionOutputDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ISolicitudDeEliminacionService {
    SolicitudDeEliminacionOutputDTO crearSolicitudDeEliminacion(SolicitudDeEliminacionInputDTO solicitudDeEliminacion);
    void aceptarSolicitud(Long idSolicitud);
    void rechazarSolicitud(Long idSolicitud);
    List<SolicitudDeEliminacionOutputDTO> obtenerSolicitudes(String tipoEstado);
}
