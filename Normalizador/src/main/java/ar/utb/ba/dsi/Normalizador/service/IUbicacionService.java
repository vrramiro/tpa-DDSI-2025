package ar.utb.ba.dsi.Normalizador.service;

import ar.utb.ba.dsi.Normalizador.models.DTOs.Output.UbicacionOutputDTO;

public interface IUbicacionService {
    public UbicacionOutputDTO obtenerUbicacion(Double latitud, Double longitud);
}
