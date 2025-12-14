package ar.utn.dssi.app_web.services.impl;

import ar.utn.dssi.app_web.dto.output.HechoOutputDTO;
import ar.utn.dssi.app_web.dto.SolicitudDTO;
import ar.utn.dssi.app_web.services.Interfaces.ISolicitudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SolicitudService implements ISolicitudService {

    private final GestionSolicitudesApiService gestionSolicitudesApiService;

    public List<SolicitudDTO> obtenerTodasLasSolicitudes() {
        return gestionSolicitudesApiService.obtenerSolicitudesEliminacion();
    }

    public List<SolicitudDTO> obtenerSolicitudesPorEstado(String estado) {
        return gestionSolicitudesApiService.obtenerSolicitudesPorEstado(estado)
                .stream()
                .filter(solicitud -> solicitud.getEstadoDeSolicitud().equalsIgnoreCase(estado))
                .collect(Collectors.toList());
    }

    public SolicitudDTO obtenerSolicitudPorId(Long solicitudId) {
        return gestionSolicitudesApiService.obtenerSolicitudPorId(solicitudId);
    }

    public HechoOutputDTO obtenerHechoPorSolicitud(Long solicitudId) {
        return gestionSolicitudesApiService.obtenerHechoPorSolicitud(solicitudId);
    }


    public SolicitudDTO crearSolicitud(SolicitudDTO solicitudOutputDTO) {
        validarDatosBasicos(solicitudOutputDTO);
        return gestionSolicitudesApiService.crearSolicitud(solicitudOutputDTO);
    }

    private void validarDatosBasicos(SolicitudDTO solicitudDTO) {
        if (solicitudDTO == null) throw new IllegalArgumentException("Solicitud vacía");
        if (solicitudDTO.getHecho() == null)
            throw new IllegalArgumentException("Debe indicar el hecho asociado");
    }

}


