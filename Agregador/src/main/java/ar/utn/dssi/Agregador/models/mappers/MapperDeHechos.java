package ar.utn.dssi.Agregador.models.mappers;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.HechoEstaticaInputDTO;
import ar.utn.dssi.Agregador.models.DTOs.inputDTO.HechoInputDTO;
import ar.utn.dssi.Agregador.models.DTOs.outputDTO.CategoriaOutputDTO;
import ar.utn.dssi.Agregador.models.DTOs.outputDTO.HechoOutputDTO;
import ar.utn.dssi.Agregador.models.DTOs.outputDTO.UbicacionOutputDTO;
import ar.utn.dssi.Agregador.models.entities.Categoria;
import ar.utn.dssi.Agregador.models.entities.Hecho;
import ar.utn.dssi.Agregador.models.entities.Multimedia;
import ar.utn.dssi.Agregador.models.entities.Ubicacion;
import java.util.List;

public class MapperDeHechos {
  static public HechoOutputDTO hechoOutputDTO(Hecho hecho) {
    HechoOutputDTO dtoHecho = new HechoOutputDTO();

    dtoHecho.setTitulo(hecho.getTitulo());
    dtoHecho.setDescripcion(hecho.getDescripcion());

    CategoriaOutputDTO categoriaOutputDTO = new CategoriaOutputDTO();
    categoriaOutputDTO.setNombre(hecho.getCategoria().getNombre());

    dtoHecho.setCategoria(categoriaOutputDTO);

    UbicacionOutputDTO ubicacionOutputDTO = new UbicacionOutputDTO();
    ubicacionOutputDTO.setLatitud(hecho.getUbicacion().getLatitud());
    ubicacionOutputDTO.setLongitud(hecho.getUbicacion().getLongitud());

    dtoHecho.setUbicacion(ubicacionOutputDTO);
    dtoHecho.setFechaAcontecimiento(hecho.getFechaAcontecimiento());
    dtoHecho.setFechaCarga(hecho.getFechaCarga());

    List<String> urlsMultimedia = hecho.getContenidoMultimedia().stream().map(Multimedia::getUrl).toList();

    dtoHecho.setContenidoMultimedia(urlsMultimedia);

    return dtoHecho;
  }

  static public Hecho hecho(HechoInputDTO hechoInputDTO) {
    Hecho hecho = new Hecho();

    hecho.setTitulo(hechoInputDTO.getTitulo());
    hecho.setDescripcion(hechoInputDTO.getDescripcion());

    Categoria categoria = new Categoria();
    categoria.setNombre(hechoInputDTO.getCategoria());

    hecho.setCategoria(categoria);

    Ubicacion ubicacion = new Ubicacion(hechoInputDTO.getUbicacion().getLatitud(), hechoInputDTO.getUbicacion().getLongitud());

    hecho.setUbicacion(ubicacion);
    hecho.setFechaAcontecimiento(hechoInputDTO.getFechaAcontecimiento());
    hecho.setFechaCarga(hechoInputDTO.getFechaCarga());

    List<Multimedia> multimedia = hechoInputDTO.getContenidoMultimedia().stream().map(Multimedia::new).toList();

    hecho.setContenidoMultimedia(multimedia);

    return hecho;
  }

  static public Hecho hechoEstaticaToHecho(HechoEstaticaInputDTO hechoEstaticaInputDTO) {
    return null;
  }
}
