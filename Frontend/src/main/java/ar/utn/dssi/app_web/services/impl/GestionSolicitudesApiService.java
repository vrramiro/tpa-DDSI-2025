package ar.utn.dssi.app_web.services.impl;

import ar.utn.dssi.app_web.dto.output.HechoOutputDTO;
import ar.utn.dssi.app_web.dto.SolicitudDTO;
import ar.utn.dssi.app_web.services.internal.WebApiCallerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GestionSolicitudesApiService {

    private final WebApiCallerService webApiCallerService;

    @Value("${agregador.service.url}")
    private String agregadorServiceUrl;

    public List<SolicitudDTO> obtenerSolicitudesEliminacion() {
        String url = agregadorServiceUrl + "/admin/solicitudes-eliminacion";
        List<SolicitudDTO> solicitudesArray = List.of(webApiCallerService.get(url, SolicitudDTO[].class));
        System.out.println(solicitudesArray);

        return (solicitudesArray);
    }

    public List<SolicitudDTO> obtenerSolicitudesPorEstado(String estado) {
        String url = agregadorServiceUrl + "/admin/solicitudes" + (estado != null && !estado.isBlank() ? "?estado=" + estado : "");
        SolicitudDTO[] solicitudesArray = webApiCallerService.get(url, SolicitudDTO[].class);

        if (solicitudesArray == null) {
            return Collections.emptyList();
        }
        return Arrays.asList(solicitudesArray);
    }


    public SolicitudDTO obtenerSolicitudPorId(Long solicitudId) {
        String url = agregadorServiceUrl + "/admin/solicitudes-eliminacion/" + solicitudId;
        return webApiCallerService.get(url, SolicitudDTO.class);
    }

    public HechoOutputDTO obtenerHechoPorSolicitud(Long solicitudId) {
        String url = agregadorServiceUrl + "/admin/solicitudes-eliminacion" + solicitudId + "/hecho";
        return webApiCallerService.get(url, HechoOutputDTO.class);
    }

    public SolicitudDTO crearSolicitud(SolicitudDTO dto) {
        String url = agregadorServiceUrl + "/public/solicitudes-eliminacion";
        return webApiCallerService.post(url, dto, SolicitudDTO.class);
    }

    public void actualizarEstado(Long idSolicitud, String nuevoEstado) {
        String url = agregadorServiceUrl + "/admin/solicitudes-eliminacion" + idSolicitud + "/estado";
        webApiCallerService.put(url, nuevoEstado, Void.class);
    }


}

