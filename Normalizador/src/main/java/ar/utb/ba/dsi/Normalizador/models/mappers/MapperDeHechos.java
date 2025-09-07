package ar.utb.ba.dsi.Normalizador.models.mappers;

import ar.utb.ba.dsi.Normalizador.models.DTOs.Output.HechoOutputDTO;
import ar.utb.ba.dsi.Normalizador.models.entities.Hecho;

public class MapperDeHechos {
    public static HechoOutputDTO hechoToOutput(Hecho hecho){
        HechoOutputDTO hechoOutput = new HechoOutputDTO();
            hechoOutput.setTitulo(hecho.getTitulo());
            hechoOutput.setDescripcion(hecho.getDescripcion());
            hechoOutput.setFechaAcontecimiento(hecho.getFechaAcontecimiento());
            hechoOutput.setFechaCarga(hecho.getFechaCarga());
            hechoOutput.setUbicacion(MapperDeUbicacion.ubicacionOutputDTO(hecho.getUbicacion()));
            hechoOutput.setCategoria(MapperDeCategorias.categoriaToOutputDTO(hecho.getCategoria()));
        return hechoOutput;
    }
}
