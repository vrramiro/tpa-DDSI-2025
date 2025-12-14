package ar.utn.dssi.app_web.services;

import ar.utn.dssi.app_web.dto.input.EstadisticaInputDTO;
import ar.utn.dssi.app_web.services.internal.WebApiCallerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GestionEstadisticaApiService {

    private final WebApiCallerService apiCaller;
    private final String urlEstadisticas;

    public GestionEstadisticaApiService(WebApiCallerService apiCaller,
                                  @Value("${estadisticas.service.url}") String urlEstadisticas) {
        this.apiCaller = apiCaller;
        this.urlEstadisticas = urlEstadisticas;
    }

    public EstadisticaInputDTO getSpam(){
        return apiCaller.get(urlEstadisticas + "/estadisticas/spam", EstadisticaInputDTO.class);
    }

    public EstadisticaInputDTO getCategoriaTop(){
        return apiCaller.get(urlEstadisticas + "/estadisticas/categoria", EstadisticaInputDTO.class);
    }

    public EstadisticaInputDTO getProvinciaPorColeccion(String handle){
        return apiCaller.get(urlEstadisticas + "/estadisticas/coleccion/" + handle + "/provincia", EstadisticaInputDTO.class);
    }

    public EstadisticaInputDTO getProvinciaPorCategoria(Long idCategoria){
        return apiCaller.get(urlEstadisticas + "/estadisticas/categoria/" + idCategoria + "/provincia", EstadisticaInputDTO.class);
    }

    public EstadisticaInputDTO getHorasPorCategoria(Long idCategoria){
        return apiCaller.get(urlEstadisticas + "/estadisticas/categoria/" + idCategoria + "/horas", EstadisticaInputDTO.class);
    }

    public byte[] getExportacion(String tipo) {
        return apiCaller.get(urlEstadisticas + "/estadisticas/exportar?tipo=" + tipo, byte[].class);
    }
}
