package ar.utn.dssi.FuenteDinamica.mappers;


import ar.utn.dssi.FuenteDinamica.dto.input.HechoInputDTO;
import ar.utn.dssi.FuenteDinamica.dto.input.HechoInputDTONormalizador;
import ar.utn.dssi.FuenteDinamica.dto.output.HechoOutputDTO;
import ar.utn.dssi.FuenteDinamica.dto.output.HechoOutputDTONormalizador;
import ar.utn.dssi.FuenteDinamica.models.entities.Hecho;
import ar.utn.dssi.FuenteDinamica.models.entities.Ubicacion;

public class MapperDeHechos {

  public static HechoOutputDTO hechoOutputDTO(Hecho hecho) {
    HechoOutputDTO dtoHecho = new HechoOutputDTO();

    dtoHecho.setTitulo(hecho.getTitulo());
    dtoHecho.setDescripcion(hecho.getDescripcion());

    dtoHecho.setTituloSanitizado(hecho.getTituloSanitizado());
    dtoHecho.setDescripcionSanitizada(hecho.getDescripcionSanitizado());

    dtoHecho.setCategoria(MapperDeCategoria.categoriaToOutputDTO(hecho.getCategoria()));
    dtoHecho.setUbicacion(MapperDeUbicacion.ubicacionOuputFromUbicacion(hecho.getUbicacion()));
    dtoHecho.setFechaAcontecimiento(hecho.getFechaAcontecimiento());
    dtoHecho.setFechaCarga(hecho.getFechaCarga());
    dtoHecho.setContenidoMultimedia(MapperContenidoMultimedia.obtenerUrlContenido(hecho.getMultimedia()));
    dtoHecho.setIdOrigen(hecho.getIdHecho());
    return dtoHecho;
  }

  public static Hecho hechoFromInputDTONormalizador(HechoInputDTONormalizador hechoInputDTO) {
    Hecho hecho = new Hecho();

    hecho.setTitulo(hechoInputDTO.getTitulo());
    hecho.setDescripcion(hechoInputDTO.getDescripcion());

    hecho.setTituloSanitizado(hechoInputDTO.getTituloSanitizado());
    hecho.setDescripcionSanitizado(hechoInputDTO.getDescripcionSanitizada());

    hecho.setUbicacion(MapperDeUbicacion.ubicacionFromInput(hechoInputDTO.getUbicacion()));
    hecho.setCategoria(MapperDeCategoria.categoriaFromCategoriaNormalizadorDTO(hechoInputDTO.getCategoria()));
    hecho.setFechaAcontecimiento(hechoInputDTO.getFechaAcontecimiento());

    return hecho;
  }

  public static Hecho hechoFromInputDTO(HechoInputDTO hechoInputDTO) {
    Hecho hecho = new Hecho();
    hecho.setTitulo(hechoInputDTO.getTitulo());
    hecho.setDescripcion(hechoInputDTO.getDescripcion());

    hecho.setFechaAcontecimiento(hechoInputDTO.getFechaAcontecimiento().atStartOfDay());

    Ubicacion ubicacion = new Ubicacion();
    ubicacion.setLatitud(hechoInputDTO.getLatitud());
    ubicacion.setLongitud(hechoInputDTO.getLongitud());
    hecho.setUbicacion(ubicacion);

    return hecho;
  }

  public static HechoOutputDTONormalizador hechoOutputNormalizadorFromHecho(Hecho hecho) {
    HechoOutputDTONormalizador output = new HechoOutputDTONormalizador();

    output.setTitulo(hecho.getTitulo());
    output.setDescripcion(hecho.getDescripcion());
    output.setCategoria(hecho.getCategoria().getNombre());
    output.setLatitud(hecho.getUbicacion().getLatitud());
    output.setLongitud(hecho.getUbicacion().getLongitud());
    output.setFechaAcontecimiento(hecho.getFechaAcontecimiento().toString());
    return output;
  }
}
