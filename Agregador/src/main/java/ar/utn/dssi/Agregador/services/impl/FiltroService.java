package ar.utn.dssi.Agregador.services.impl;

import ar.utn.dssi.Agregador.models.DTOs.inputDTO.FiltroInputDTO;
import ar.utn.dssi.Agregador.models.entities.Filtro;
import ar.utn.dssi.Agregador.models.entities.criteriosDeFiltrado.ICriterioDeFiltrado;
import ar.utn.dssi.Agregador.models.entities.criteriosDeFiltrado.impl.*;
import ar.utn.dssi.Agregador.services.IFiltrosService;
import ar.utn.dssi.Agregador.models.entities.criteriosDeFiltrado.TipoCriterio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FiltroService implements IFiltrosService {
    public List<ICriterioDeFiltrado> criteriosDelFiltro;

    @Override
    public Filtro crearFiltro(FiltroInputDTO filtroInputDTO) {
        List<ICriterioDeFiltrado> criterios = new ArrayList<>();

        if (filtroInputDTO.getCategoria() != null && !filtroInputDTO.getCategoria().isBlank()) {
            criterios.add(new CriterioPorCategoria(filtroInputDTO.getCategoria()));
        }

        if (filtroInputDTO.getFecha_acontecimiento_desde() != null) {
            criterios.add(new CriterioFechaDesde(filtroInputDTO.getFecha_acontecimiento_desde()));
        }

        if (filtroInputDTO.getFecha_acontecimiento_hasta() != null) {
            criterios.add(new CriterioFechaHasta(filtroInputDTO.getFecha_acontecimiento_hasta()));
        }

        if (filtroInputDTO.getLatitud() != null && filtroInputDTO.getLongitud() != null) {
            criterios.add(new CriterioUbicacion(filtroInputDTO.getLatitud(), filtroInputDTO.getLongitud()));
        }

        if (filtroInputDTO.getIdFuente() != null) {
            criterios.add(new CriterioPorFuente(filtroInputDTO.getIdFuente()));
        }

        return Filtro.builder()
                .criteriosDeFiltro(criterios)
                .build();
    }

}

