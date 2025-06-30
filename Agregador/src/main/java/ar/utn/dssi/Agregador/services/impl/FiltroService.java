package ar.utn.dssi.Agregador.services.impl;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.FiltroInputDTO;
import ar.utn.dssi.Agregador.models.entities.Filtro;
import ar.utn.dssi.Agregador.services.IFiltrosService;

public class FiltroService implements IFiltrosService {
    @Override
    public Filtro crearFiltro(FiltroInputDTO filtroInputDTO) {

            Filtro filtro = new Filtro();

            //TODO: ASIGNAR ATRIBUTOS DEL INPUT

            return filtro;
    }
}
