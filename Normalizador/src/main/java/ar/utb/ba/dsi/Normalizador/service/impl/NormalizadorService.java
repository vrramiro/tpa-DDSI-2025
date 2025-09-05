package ar.utb.ba.dsi.Normalizador.service.impl;

import ar.utb.ba.dsi.Normalizador.models.DTOs.Output.UbicacionOutputDTO;
import ar.utb.ba.dsi.Normalizador.models.entities.IUbicacionAdapter;
import ar.utb.ba.dsi.Normalizador.service.INormalizadorService;
import org.springframework.stereotype.Service;

@Service
public class NormalizadorService implements INormalizadorService {
    private final IUbicacionAdapter adapter;

    public NormalizadorService(IUbicacionAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public UbicacionOutputDTO obtenerUbicacion(Double latitud, Double longitud) {
        try {
            return adapter.obtenerUbicacionDeAPI(latitud, longitud).block();
        } catch (Exception e) {
            throw new RuntimeException("No se pudo obtener la ubicación", e);
        }
    }
}
