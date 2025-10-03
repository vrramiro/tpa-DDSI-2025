package ar.utn.dssi.Agregador.models.mappers;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.fuentes.HechoFuenteDinamicaInputDTO;
import ar.utn.dssi.Agregador.models.DTOs.inputDTO.fuentes.HechoFuenteEstaticaIntputDTO;
import ar.utn.dssi.Agregador.models.DTOs.inputDTO.fuentes.HechoFuenteProxyInputDTO;
import ar.utn.dssi.Agregador.models.DTOs.outputDTO.HechoOutputDTO;
import ar.utn.dssi.Agregador.models.entities.Categoria;
import ar.utn.dssi.Agregador.models.entities.ContenidoMultimedia;
import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.Ubicacion;

import java.util.stream.Collectors;

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


  // HechoFuenteProxyInputDTO -> Hecho
  static public Hecho hechoFromInputDTOProxy(HechoFuenteProxyInputDTO input) {
    Hecho hecho = new Hecho();
    hecho.setIdEnFuente(input.getIdExterno());
    hecho.setTitulo(input.getTitulo());
    hecho.setDescripcion(input.getDescripcion());
    hecho.setTituloSanitizado(input.getTituloSanitizado());
    hecho.setDescripcionSanitizado(input.getDescripcionSanitizada());
    hecho.setFechaAcontecimiento(input.getFechaAcontecimiento());
    hecho.setFechaCarga(input.getFechaCarga());
    hecho.setVisible(true);

    // Fuente externa se debería setear aparte (lookup en BD usando idFuenteExterna)

    return hecho;
  }

  // HechoFuenteEstaticaInputDTO -> Hecho
  static public Hecho hechoFromInputDTOEstatica(HechoFuenteEstaticaIntputDTO input) {
    Hecho hecho = new Hecho();
    hecho.setIdEnFuente(input.getIdExterno());
    hecho.setTitulo(input.getTitulo());
    hecho.setDescripcion(input.getDescripcion());
    hecho.setTituloSanitizado(input.getTituloSanitizado());
    hecho.setDescripcionSanitizado(input.getDescripcionSanitizada());

    Categoria categoria = new Categoria();
    categoria.setId(input.getCategoria().getId());
    categoria.setNombre(input.getCategoria().getNombre());
    hecho.setCategoria(categoria);

    Ubicacion ubicacion = new Ubicacion();
    ubicacion.setLatitud(input.getUbicacion().getLatitud());
    ubicacion.setLongitud(input.getUbicacion().getLongitud());
    ubicacion.setPais(input.getUbicacion().getPais());
    ubicacion.setCiudad(ubicacion.getCiudad());
    ubicacion.setProvincia(ubicacion.getProvincia());
    hecho.setUbicacion(ubicacion);


    hecho.setFechaAcontecimiento(input.getFechaAcontecimiento());
    hecho.setFechaCarga(input.getFechaCarga());
    hecho.setVisible(true);
    return hecho;
  }

  // HechoFuenteDinamicaInputDTO -> Hecho

}
