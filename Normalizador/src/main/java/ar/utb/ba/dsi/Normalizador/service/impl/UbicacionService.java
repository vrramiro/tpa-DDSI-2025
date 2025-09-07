package ar.utb.ba.dsi.Normalizador.service.impl;

import ar.utb.ba.dsi.Normalizador.models.DTOs.Output.UbicacionOutputDTO;
import ar.utb.ba.dsi.Normalizador.models.entities.AdapterUbicacion.IUbicacionAdapter;
import ar.utb.ba.dsi.Normalizador.models.entities.Ubicacion;
import ar.utb.ba.dsi.Normalizador.models.mappers.MapperDeUbicacion;
import ar.utb.ba.dsi.Normalizador.service.IUbicacionService;
import org.springframework.stereotype.Service;

@Service
public class UbicacionService implements IUbicacionService {
    private final IUbicacionAdapter adapter;

    public UbicacionService(IUbicacionAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public Ubicacion obtenerUbicacion(Double latitud, Double longitud) {
        try {
            Ubicacion ubicacion = adapter.obtenerUbicacionDeAPI(latitud, longitud).block();

            return ubicacion;
        } catch (Exception e) {
            throw new RuntimeException("No se pudo obtener la ubicación", e);
        }
    }

    @Override
    public UbicacionOutputDTO obtenerUbicacionOutPut(Double latitud, Double longitud) {
        return MapperDeUbicacion.ubicacionOutputDTO(this.obtenerUbicacion(latitud, longitud));
    }
}
