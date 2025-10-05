package ar.utn.dssi.Agregador.models.mappers;


import ar.utn.dssi.Agregador.models.DTOs.inputDTO.fuentes.HechoFuenteProxyInputDTO;
import ar.utn.dssi.Agregador.models.DTOs.outputDTO.HechoOutputDTO;

import ar.utn.dssi.Agregador.models.entities.Hecho;


public class MapperDeHechos {

  // Hecho -> HechoOutputDTO
  static public HechoOutputDTO hechoToOutputDTO(Hecho hecho) {
    HechoOutputDTO dto = new HechoOutputDTO();
    dto.setTitulo(hecho.getTitulo());
    dto.setDescripcion(hecho.getDescripcion());
    dto.setCategoria(hecho.getCategoria());
    dto.setUbicacion(hecho.getUbicacion());
    dto.setFechaAcontecimiento(hecho.getFechaAcontecimiento());
    dto.setFechaCarga(hecho.getFechaCarga());

    dto.setContenidoMultimedia(null); //TODO: VER GESTION MULTIMEDIA

    return dto;
  }

}
