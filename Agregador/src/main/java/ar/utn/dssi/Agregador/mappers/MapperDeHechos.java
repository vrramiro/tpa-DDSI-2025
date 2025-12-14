package ar.utn.dssi.Agregador.mappers;

import ar.utn.dssi.Agregador.dto.output.ContenidoMultimediaOuputDTO;
import ar.utn.dssi.Agregador.dto.output.HechoOutputDTO;
import ar.utn.dssi.Agregador.dto.output.UbicacionOutputDTO;
import ar.utn.dssi.Agregador.models.entities.Hecho;

public class MapperDeHechos {
  static public HechoOutputDTO hechoToOutputDTO(Hecho hecho) {
    HechoOutputDTO dto = new HechoOutputDTO();
    dto.setId(hecho.getId());
    dto.setTitulo(hecho.getTitulo());
    dto.setDescripcion(hecho.getDescripcion());
    dto.setCategoria(hecho.getCategoria().getNombre());

    UbicacionOutputDTO ubicacion = new UbicacionOutputDTO();
    ubicacion.setPais(hecho.getUbicacion().getPais());
    ubicacion.setProvincia(hecho.getUbicacion().getProvincia());
    ubicacion.setCiudad(hecho.getUbicacion().getCiudad());
    ubicacion.setLatitud(hecho.getUbicacion().getLatitud());
    ubicacion.setLongitud(hecho.getUbicacion().getLongitud());
    dto.setUbicacion(ubicacion);

    dto.setFechaAcontecimiento(hecho.getFechaAcontecimiento());
    dto.setFechaCarga(hecho.getFechaCarga());

    dto.setContenidoMultimedia(hecho.getContenidoMultimedia()
        .stream()
        .map(contenido -> {
          ContenidoMultimediaOuputDTO contenidoDTO = new ContenidoMultimediaOuputDTO();
          contenidoDTO.setUrl(contenido.getUrl());
          return contenidoDTO;
        })
        .toList()
    );
    dto.setAutor(hecho.getAutor());
    dto.setVisible(hecho.getVisible());
    return dto;
  }
}
