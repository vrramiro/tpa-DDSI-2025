package ar.utn.dssi.FuenteEstatica.models.mappers;

import ar.utn.dssi.FuenteEstatica.models.DTOs.input.HechoInputDTO;
import ar.utn.dssi.FuenteEstatica.models.DTOs.input.HechoInputDTONormalizador;
import ar.utn.dssi.FuenteEstatica.models.DTOs.output.HechoOutputDTO;
import ar.utn.dssi.FuenteEstatica.models.DTOs.output.HechoOutputDTONormalizador;
import ar.utn.dssi.FuenteEstatica.models.entities.contenido.Hecho;

import java.time.LocalDateTime;

public class MapperDeHechos {

    public static HechoOutputDTO hechoOutputDTO(Hecho hecho){
        HechoOutputDTO dtoHecho = new HechoOutputDTO();

        dtoHecho.setTitulo(hecho.getTitulo());
        dtoHecho.setDescripcion(hecho.getDescripcion());
        dtoHecho.setCategoria(hecho.getCategoria());
        dtoHecho.setUbicacion(hecho.getUbicacion());
        dtoHecho.setFechaAcontecimiento(hecho.getFechaAcontecimiento());
        dtoHecho.setFechaCarga(hecho.getFechaCarga());
        dtoHecho.setIdHechoOrigen(hecho.getIdHecho());
        return dtoHecho;
    }

    public static Hecho hechoFromInputDTONormalizador(HechoInputDTONormalizador hechoInputDTO){
        Hecho hecho = new Hecho();
            hecho.setTitulo(hechoInputDTO.getTitulo());
            hecho.setDescripcion(hechoInputDTO.getDescripcion());
            hecho.setUbicacion(MapperDeUbicacion.ubicacionFromInput(hechoInputDTO.getUbicacion()));
            hecho.setCategoria(MapperDeCategorias.categoriaFromInputDTO(hechoInputDTO.getCategoria()));
            hecho.setFechaAcontecimiento(hechoInputDTO.getFechaAcontecimiento());
            hecho.setFechaCarga(hechoInputDTO.getFechaCarga());

        return null;
    }

    public static HechoOutputDTONormalizador hechoFromInputToOutputNormalizador(HechoInputDTO hechoInputDTO){
        HechoOutputDTONormalizador hecho = new HechoOutputDTONormalizador();
            hecho.setTitulo(hechoInputDTO.getTitulo());
            hecho.setDescripcion(hechoInputDTO.getDescripcion());
            hecho.setLatitud(hechoInputDTO.getLatitud());
            hecho.setLongitud(hechoInputDTO.getLongitud());
            hecho.setFechaAcontecimiento(hechoInputDTO.getFechaAcontecimiento().toString());
            hecho.setFechaCarga(LocalDateTime.now().toString());
        return hecho;
    }
}
