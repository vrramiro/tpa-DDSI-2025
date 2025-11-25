package ar.utn.dssi.app_web.services.Interfaces;

import ar.utn.dssi.app_web.dto.output.HechoOutputDTO;
import ar.utn.dssi.app_web.dto.SolicitudDTO;

import java.util.List;

public interface ISolicitudService {
    List<SolicitudDTO> obtenerTodasLasSolicitudes();
    List<SolicitudDTO> obtenerSolicitudesPorEstado(String estado);
    SolicitudDTO obtenerSolicitudPorId(Long solicitudId);
    HechoOutputDTO obtenerHechoPorSolicitud(Long solicitudId);
    void actualizarEstado(Long idSolicitud, String estado);
    SolicitudDTO crearSolicitud(SolicitudDTO solicitudOutputDTO);
}
