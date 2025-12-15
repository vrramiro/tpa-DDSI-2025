package ar.utn.dssi.app_web.services;

import ar.utn.dssi.app_web.dto.input.EstadisticaInputDTO;
import ar.utn.dssi.app_web.services.internal.WebApiCallerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class GestionEstadisticaApiService {

    private static final Logger log = LoggerFactory.getLogger(GestionEstadisticaApiService.class);

    private final WebApiCallerService apiCaller;
    private final String urlEstadisticas;

    public GestionEstadisticaApiService(WebApiCallerService apiCaller,
                                  @Value("${estadisticas.service.url}") String urlEstadisticas) {
        this.apiCaller = apiCaller;
        this.urlEstadisticas = urlEstadisticas;
    }

    public EstadisticaInputDTO getSpam() {
        try {
            return apiCaller.getPublic(urlEstadisticas + "/estadisticas/spam", EstadisticaInputDTO.class);
        } catch (Exception e){
           log.error("Error al obtener estadísticas de SPAM: {}", e.getMessage());
           return null;
        }
    }
    public EstadisticaInputDTO getCategoriaTop(){
        try {
            return apiCaller.getPublic(urlEstadisticas + "/estadisticas/categoria", EstadisticaInputDTO.class);
        } catch (Exception e) {
            log.error("Error al obtener categoría TOP: {}", e.getMessage());
            return null;
        }
    }

    public EstadisticaInputDTO getProvinciaPorColeccion(String handle){
        try {
            return apiCaller.getPublic(urlEstadisticas + "/estadisticas/coleccion/" + handle + "/provincia", EstadisticaInputDTO.class);
        } catch (Exception e) {
            log.error("Error al obtener provincia por colección (handle: {}): {}", handle, e.getMessage());
            return null;
        }
    }

    public EstadisticaInputDTO getProvinciaPorCategoria(Long idCategoria){
        try {
            return apiCaller.getPublic(urlEstadisticas + "/estadisticas/categoria/" + idCategoria + "/provincia", EstadisticaInputDTO.class);
        } catch (Exception e) {
            log.error("Error al obtener provincia por categoría (id: {}): {}", idCategoria, e.getMessage());
            return null;
        }
    }

    public EstadisticaInputDTO getHorasPorCategoria(Long idCategoria){
        try {
            return apiCaller.getPublic(urlEstadisticas + "/estadisticas/categoria/" + idCategoria + "/horas", EstadisticaInputDTO.class);
        } catch (Exception e) {
            log.error("Error al obtener horas por categoría (id: {}): {}", idCategoria, e.getMessage());
            return null;
        }
    }

    public byte[] getExportacion(String tipo) {
        try {
            return apiCaller.getPublic(urlEstadisticas + "/estadisticas/exportar?tipo=" + tipo, byte[].class);
        } catch (Exception e) {
            log.error("Error al exportar estadísticas: {}", e.getMessage());
            return null;
        }
    }
}
