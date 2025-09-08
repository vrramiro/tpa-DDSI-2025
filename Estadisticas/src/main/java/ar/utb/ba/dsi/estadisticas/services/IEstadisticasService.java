package ar.utb.ba.dsi.estadisticas.services;

import ar.utb.ba.dsi.estadisticas.models.DTOs.outputDTOs.EstadisticaOutputDTO;
import ar.utb.ba.dsi.estadisticas.models.entities.TipoArchivo;
import ar.utb.ba.dsi.estadisticas.models.repositories.IEstadisticasReposotory;

import java.io.File;
import java.util.List;

public interface  IEstadisticasService {
    void calcularEstadisticas();
    EstadisticaOutputDTO getProvinciasConMasHechosColeccion(Long idColeccion);
    EstadisticaOutputDTO getCategoriaConMasHechos();
    EstadisticaOutputDTO getProvinciasConMasHechoCategoria(Long idCategoria);
    EstadisticaOutputDTO getHorasConMasHechosCategoria(Long idCategoria);
    EstadisticaOutputDTO getCantidadSpam();
    File getArchivoEstadisticas(TipoArchivo tipo);
}
