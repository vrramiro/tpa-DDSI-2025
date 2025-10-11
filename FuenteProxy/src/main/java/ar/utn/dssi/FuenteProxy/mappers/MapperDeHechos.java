package ar.utn.dssi.FuenteProxy.mappers;


import ar.utn.dssi.FuenteProxy.dto.output.HechoOutputDTO;
import ar.utn.dssi.FuenteProxy.dto.output.HechoOutputDTONormalizador;
import ar.utn.dssi.FuenteProxy.models.entities.Hecho;

public class MapperDeHechos {
    public static HechoOutputDTO hechoOutputDTO(Hecho hecho){
        HechoOutputDTO dtoHecho = new HechoOutputDTO();

        dtoHecho.setTitulo(hecho.getTitulo());
        dtoHecho.setDescripcion(hecho.getDescripcion());
        dtoHecho.setTituloSanitizado(hecho.getTituloSanitizado());
        dtoHecho.setDescripcionSanitizada(hecho.getDescripcionSanitizada());
        dtoHecho.setCategoria(hecho.getCategoria());
        dtoHecho.setUbicacion(hecho.getUbicacion());
        dtoHecho.setFechaAcontecimiento(hecho.getFechaAcontecimiento());
        dtoHecho.setFechaCarga(hecho.getFechaCarga());
        dtoHecho.setIdOrigen(hecho.getId());
        return dtoHecho;
    }

    public static HechoOutputDTONormalizador hechoToOutputNormalizador(Hecho hechoInput){
        HechoOutputDTONormalizador hecho = new HechoOutputDTONormalizador();
        hecho.setTitulo(hechoInput.getTitulo());
        hecho.setDescripcion(hechoInput.getDescripcion());
        hecho.setCategoria(hechoInput.getCategoria().getNombre());
        hecho.setLatitud(hechoInput.getUbicacion().getLatitud());
        hecho.setLongitud(hechoInput.getUbicacion().getLongitud());
        hecho.setFechaAcontecimiento(hechoInput.getFechaAcontecimiento().toString());
        //hecho.setFechaCarga(hechoInput.getFechaCarga().toString()); //TODO hacer cambio a LocalDateTime
        return hecho;
    }

}
