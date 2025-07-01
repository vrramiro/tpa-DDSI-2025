package ar.utn.dssi.Agregador.models.entities;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.HechoInputDTO;
import ar.utn.dssi.Agregador.models.DTOs.outputDTO.HechoOutputDTO;

public class Mapper {

    public static Hecho hechoInputToHecho(HechoInputDTO hecho) {
        Hecho hechoObtenido = new Hecho();
        hechoObtenido.setIdFuente(hecho.getIdEnFuente());
        hechoObtenido.setTitulo(hecho.getTitulo());
        hechoObtenido.setDescripcion(hecho.getDescripcion());
        hechoObtenido.setCategoria(hecho.getCategoria());
        hechoObtenido.setUbicacion(hecho.getUbicacion());
        hechoObtenido.setFechaAcontecimiento(hecho.getFechaAcontecimiento());
        hechoObtenido.setFechaCarga(hecho.getFechaCarga());
        hechoObtenido.setOrigen(hecho.getOrigen());
        hechoObtenido.setContenidoMultimedia(hecho.getContenidoMultimedia());

        return hechoObtenido;
    }
}
