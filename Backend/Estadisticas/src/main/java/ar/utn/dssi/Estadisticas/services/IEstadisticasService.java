package ar.utn.dssi.Estadisticas.services;

import ar.utn.dssi.Estadisticas.models.DTOs.outputs.EstadisticaOutputDTO;
import ar.utn.dssi.Estadisticas.models.entities.TipoArchivo;

import java.io.File;

public interface  IEstadisticasService {
    void calcularEstadisticas();
    EstadisticaOutputDTO getProvinciasConMasHechosColeccion(Long idColeccion);
    EstadisticaOutputDTO getCategoriaConMasHechos();
    EstadisticaOutputDTO getProvinciasConMasHechoCategoria(Long idCategoria);
    EstadisticaOutputDTO getHorasConMasHechosCategoria(Long idCategoria);
    EstadisticaOutputDTO getCantidadSpam();
    File getArchivoEstadisticas(TipoArchivo tipo);
}
