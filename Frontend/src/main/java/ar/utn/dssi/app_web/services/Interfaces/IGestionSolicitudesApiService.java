package ar.utn.dssi.app_web.services.interfaces;

import ar.utn.dssi.app_web.dto.output.HechoOutputDTO;
import ar.utn.dssi.app_web.dto.SolicitudDTO;

import java.util.List;

public interface IGestionSolicitudesApiService {
    List<SolicitudDTO> obtenerSolicitudes();
    List<SolicitudDTO> obtenerSolicitudesPorEstado(String estado);
    SolicitudDTO obtenerSolicitudPorId(Long solicitudId);
    HechoOutputDTO obtenerHechoPorSolicitud(Long solicitudId);
    SolicitudDTO crearSolicitud(SolicitudDTO dto);
    void actualizarEstado(Long idSolicitud, String nuevoEstado);
}
