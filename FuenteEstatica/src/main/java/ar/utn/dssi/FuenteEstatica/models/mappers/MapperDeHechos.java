package ar.utn.dssi.FuenteEstatica.models.mappers;

import ar.utn.dssi.FuenteEstatica.models.DTOs.input.HechoInputDTONormalizador;
import ar.utn.dssi.FuenteEstatica.models.DTOs.output.HechoOutputDTO;
import ar.utn.dssi.FuenteEstatica.models.DTOs.output.HechoOutputDTONormalizador;
import ar.utn.dssi.FuenteEstatica.models.entities.contenido.Hecho;

public class MapperDeHechos {

  public static HechoOutputDTO hechoOutputDTO(Hecho hecho) {
    HechoOutputDTO dtoHecho = new HechoOutputDTO();

    dtoHecho.setTitulo(hecho.getTitulo());
    dtoHecho.setDescripcion(hecho.getDescripcion());
    dtoHecho.setCategoria(MapperDeCategoria.outputDTOFromCategoria(hecho.getCategoria()));
    dtoHecho.setUbicacion(MapperDeUbicacion.OutputFromUbicacion(hecho.getUbicacion()));
    dtoHecho.setFechaAcontecimiento(hecho.getFechaAcontecimiento());
    dtoHecho.setFechaCarga(hecho.getFechaCarga());
    dtoHecho.setIdOrigen(hecho.getId());
    return dtoHecho;
  }

  public static Hecho hechoFromInputDTONormalizador(HechoInputDTONormalizador hechoInputDTO) {
    Hecho hecho = new Hecho();
    hecho.setTitulo(hechoInputDTO.getTitulo());
    hecho.setDescripcion(hechoInputDTO.getDescripcion());
    hecho.setCategoria(MapperDeCategoria.categoriaFromInput(hechoInputDTO.getCategoria()));
    hecho.setUbicacion(MapperDeUbicacion.ubicacionFromInput(hechoInputDTO.getUbicacion()));
    hecho.setFechaAcontecimiento(hechoInputDTO.getFechaAcontecimiento());
    return hecho;
  }

  public static HechoOutputDTONormalizador hechoToOutputNormalizador(Hecho hechoInput) {
    HechoOutputDTONormalizador hecho = new HechoOutputDTONormalizador();
    hecho.setTitulo(hechoInput.getTitulo());
    hecho.setDescripcion(hechoInput.getDescripcion());
    hecho.setCategoria(hechoInput.getCategoria().getCategoria());
    hecho.setLatitud(hechoInput.getUbicacion().getLatitud());
    hecho.setLongitud(hechoInput.getUbicacion().getLongitud());
    hecho.setFechaAcontecimiento(hechoInput.getFechaAcontecimiento().toString());
    return hecho;
  }

}
