package ar.utb.ba.dsi.Normalizador.service;

import ar.utb.ba.dsi.Normalizador.models.DTOs.UbicacionResponse;
import org.springframework.http.ResponseEntity;

public interface INormalizadorService {
    public UbicacionResponse obtenerUbicacion(Double latitud, Double longitud);
}
