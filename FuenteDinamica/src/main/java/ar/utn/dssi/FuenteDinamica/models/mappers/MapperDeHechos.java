package ar.utn.dssi.FuenteDinamica.models.mappers;


import ar.utn.dssi.FuenteDinamica.models.DTOs.inputs.HechoInputDTO;
import ar.utn.dssi.FuenteDinamica.models.DTOs.inputs.HechoInputDTONormalizador;
import ar.utn.dssi.FuenteDinamica.models.DTOs.outputs.HechoOutputDTO;
import ar.utn.dssi.FuenteDinamica.models.DTOs.outputs.HechoOutputDTONormalizador;
import ar.utn.dssi.FuenteDinamica.models.entities.Hecho;
import java.time.LocalDateTime;

public class MapperDeHechos {

    public static HechoOutputDTO hechoOutputDTO(Hecho hecho) {
        HechoOutputDTO dtoHecho = new HechoOutputDTO();

        dtoHecho.setTitulo(hecho.getTitulo());
        dtoHecho.setDescripcion(hecho.getDescripcion());
        dtoHecho.setTituloSanitizado(hecho.getTituloSanitizado());
        dtoHecho.setDescripcionSanitizado(hecho.getDescripcionSanitizado());
        dtoHecho.setCategoria(MapperDeCategoria.categoriaToOutputDTO(hecho.getCategoria()));
        dtoHecho.setUbicacion(MapperDeUbicacion.ubicacionOuputFromUbicacion(hecho.getUbicacion()));
        dtoHecho.setFechaAcontecimiento(hecho.getFechaAcontecimiento());
        dtoHecho.setFechaCarga(hecho.getFechaCarga());
        dtoHecho.setContenidoMultimedia(MapperContenidoMultimedia.obtenerUrlContenido(hecho.getMultimedia()));
        dtoHecho.setIdHechoOrigen(hecho.getIdHecho());
        return dtoHecho;
    }

    public static Hecho hechoFromInputDTONormalizador(HechoInputDTONormalizador hechoInputDTO) {
        Hecho hecho = new Hecho();
            hecho.setTituloSanitizado(hechoInputDTO.getTitulo());
            hecho.setDescripcionSanitizado(hechoInputDTO.getDescripcion());
            hecho.setUbicacion(MapperDeUbicacion.ubicacionFromInput(hechoInputDTO.getUbicacion()));
            hecho.setCategoria(MapperDeCategoria.categoriaFromInputDTO(hechoInputDTO.getCategoria()));
            hecho.setFechaAcontecimiento(hechoInputDTO.getFechaAcontecimiento());
            hecho.setFechaCarga(hechoInputDTO.getFechaCarga());

        return hecho;
    }

    public static Hecho hechoFromInputDTO(HechoInputDTO hechoInputDTO) {
        Hecho hecho = new Hecho();
        hecho.setTitulo(hechoInputDTO.getTitulo());
        hecho.setDescripcion(hechoInputDTO.getDescripcion());
        hecho.setCategoria(MapperDeCategoria.categoriaFromInputDTO(hechoInputDTO.getCategoria()));   //MAPPER DE CATEGORIA
        hecho.setFechaAcontecimiento(hechoInputDTO.getFechaAcontecimiento());
        return hecho;
    }

    public static HechoOutputDTONormalizador hechoOutputNormalizadorFromHecho(Hecho hecho) {
        HechoOutputDTONormalizador output = new HechoOutputDTONormalizador();
            output.setTitulo(hecho.getTitulo());
            output.setDescripcion(hecho.getDescripcion());
            output.setLatitud(hecho.getUbicacion().getLatitud());
            output.setLongitud(hecho.getUbicacion().getLongitud());
            output.setFechaAcontecimiento(hecho.getFechaAcontecimiento().toString());
        return output;
    }
}
