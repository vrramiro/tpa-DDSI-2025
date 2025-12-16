package ar.utn.dssi.app_web.services.impl;

import ar.utn.dssi.app_web.dto.input.SolicitudProcesadaInputDTO;
import ar.utn.dssi.app_web.dto.output.HechoOutputDTO;
import ar.utn.dssi.app_web.dto.SolicitudDTO;
import ar.utn.dssi.app_web.services.internal.WebApiCallerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class GestionSolicitudesApiService {

    private final WebApiCallerService webApiCallerService;

    @Value("${agregador.service.url}")
    private String agregadorServiceUrl;

    public List<SolicitudDTO> obtenerSolicitudesEliminacion() {
        String url = agregadorServiceUrl + "/admin/solicitudes-eliminacion";

        SolicitudDTO[] solicitudesArray = webApiCallerService.get(url, SolicitudDTO[].class);

        if (solicitudesArray == null) {
            return Collections.emptyList();
        }

        System.out.println(Arrays.toString(solicitudesArray));
        return Arrays.asList(solicitudesArray);
    }

    public List<SolicitudDTO> obtenerSolicitudesPorEstado(String estado) {
        String url = agregadorServiceUrl + "/admin/solicitudes-eliminacion" + (estado != null && !estado.isBlank() ? "?estado=" + estado : "");

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
        String url = agregadorServiceUrl + "/admin/solicitudes-eliminacion/" + solicitudId + "/hecho";
        return webApiCallerService.get(url, HechoOutputDTO.class);
    }

    public SolicitudDTO crearSolicitud(SolicitudDTO dto) {
        String url = agregadorServiceUrl + "/public/solicitudes-eliminacion";

        Map<String, Object> requestBody = new HashMap<>();
        if (dto.getHecho() != null) {
            requestBody.put("idHecho", dto.getHecho().getId());
        }
        requestBody.put("descripcion", dto.getDescripcion());

        return webApiCallerService.post(url, requestBody, SolicitudDTO.class);
    }



    public void procesarSolicitud(Long idSolicitud, String nuevoEstado) {
        String url = agregadorServiceUrl + "/admin/solicitudes-eliminacion/procesar/" + idSolicitud;
        SolicitudProcesadaInputDTO body = new SolicitudProcesadaInputDTO(nuevoEstado);
        webApiCallerService.post(url, body, Void.class);
    }
}