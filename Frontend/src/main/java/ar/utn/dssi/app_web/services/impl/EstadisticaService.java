package ar.utn.dssi.app_web.services.impl;

import ar.utn.dssi.app_web.dto.input.EstadisticaInputDTO;
import ar.utn.dssi.app_web.services.Interfaces.IEstadisticaService;
import ar.utn.dssi.app_web.services.GestionEstadisticaApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EstadisticaService implements IEstadisticaService {

    private final GestionEstadisticaApiService estadisticasApiService;

    @Override
    public EstadisticaInputDTO obtenerCantidadSpam() {
        return estadisticasApiService.getSpam();
    }

    @Override
    public EstadisticaInputDTO obtenerCategoriaMasHechos() {
        return estadisticasApiService.getCategoriaTop();
    }

    @Override
    public EstadisticaInputDTO obtenerProvinciaPorColeccion(Long idColeccion) {
        return estadisticasApiService.getProvinciaPorColeccion(idColeccion);
    }

    @Override
    public EstadisticaInputDTO obtenerProvinciaPorCategoria(Long idCategoria) {
        return estadisticasApiService.getProvinciaPorCategoria(idCategoria);
    }

    @Override
    public EstadisticaInputDTO obtenerHorasPorCategoria(Long idCategoria) {
        return estadisticasApiService.getHorasPorCategoria(idCategoria);
    }

    @Override
    public byte[] exportarEstadisticas(String tipoArchivo) {
        return estadisticasApiService.getExportacion(tipoArchivo);
    }
}
