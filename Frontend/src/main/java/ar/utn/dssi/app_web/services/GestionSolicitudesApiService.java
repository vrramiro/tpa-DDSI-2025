package ar.utn.dssi.app_web.services;

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

    public List<SolicitudDTO> obtenerSolicitudes() {
        String url = agregadorServiceUrl + "/admin/solicitudes";
        List<SolicitudDTO> solicitudesArray = List.of(webApiCallerService.get(url, SolicitudDTO[].class));
        System.out.println(solicitudesArray);
        if (solicitudesArray == null) {
            return Collections.emptyList();
        }

        return (solicitudesArray);
    }

    public List<SolicitudDTO> obtenerSolicitudesPorEstado(String estado) {
        String url = agregadorServiceUrl + "/admin/solicitudes" + (estado != null && !estado.isBlank() ? "?estado=" + estado : "");
        return Arrays.asList(webApiCallerService.get(url, SolicitudDTO[].class));
    }

    public SolicitudDTO obtenerSolicitudPorId(Long solicitudId) {
        String url = agregadorServiceUrl + "/admin/solicitudes/" + solicitudId;
        return webApiCallerService.get(url, SolicitudDTO.class);
    }

    public HechoOutputDTO obtenerHechoPorSolicitud(Long solicitudId) {
        String url = agregadorServiceUrl + "/admin/solicitudes/" + solicitudId + "/hecho";
        return webApiCallerService.get(url, HechoOutputDTO.class);
    }

    public SolicitudDTO crearSolicitud(SolicitudDTO dto) {
        String url = agregadorServiceUrl + "/admin/solicitudes";
        return webApiCallerService.post(url, dto, SolicitudDTO.class);
    }

    public void actualizarEstado(Long idSolicitud, String nuevoEstado) {
        String url = agregadorServiceUrl + "/admin/solicitudes/" + idSolicitud + "/estado";
        webApiCallerService.put(url, nuevoEstado, Void.class);
    }
}

