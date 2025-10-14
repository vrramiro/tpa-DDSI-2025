package ar.utb.ba.dsi.Normalizador.mappers;

import ar.utb.ba.dsi.Normalizador.dto.output.HechoOutputDTO;
import ar.utb.ba.dsi.Normalizador.models.entities.Hecho;

public class MapperDeHechos {
  public static HechoOutputDTO hechoToOutput(Hecho hecho) {
    HechoOutputDTO hechoOutput = new HechoOutputDTO();
    hechoOutput.setTitulo(hecho.getTitulo());
    hechoOutput.setDescripcion(hecho.getDescripcion());
    hechoOutput.setTituloSanitizado(hecho.getTituloSanitizado());
    hechoOutput.setDescripcionSanitizada(hecho.getDescripcionSanitizada());
    hechoOutput.setFechaAcontecimiento(hecho.getFechaAcontecimiento());
    hechoOutput.setUbicacion(MapperDeUbicacion.ubicacionOutputDTO(hecho.getUbicacion()));
    hechoOutput.setCategoria(MapperDeCategorias.categoriaToOutputDTO(hecho.getCategoria()));
    return hechoOutput;
  }
}
